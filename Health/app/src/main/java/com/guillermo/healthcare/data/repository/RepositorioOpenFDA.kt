package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.remote.ClienteRetrofit
import com.guillermo.healthcare.data.remote.dto.EtiquetaDto
import com.guillermo.healthcare.data.remote.dto.MedicamentoDto
import javax.inject.Inject

sealed class ResultadoApi<out T> {
    data class Exito<T>(val datos: T) : ResultadoApi<T>()
    data class Error(val mensaje: String) : ResultadoApi<Nothing>()
    object Cargando : ResultadoApi<Nothing>()
}

class RepositorioOpenFDA @Inject constructor(
    private val cliente: ClienteRetrofit
) {
    suspend fun buscarMedicamentos(consulta: String): ResultadoApi<List<MedicamentoDto>> {
        return try {
            val respuesta = cliente.api.buscarMedicamentos("brand_name:$consulta")
            if (respuesta.resultados != null && respuesta.resultados.isNotEmpty()) {
                ResultadoApi.Exito(respuesta.resultados)
            } else {
                ResultadoApi.Error("No se encontraron resultados para '$consulta'")
            }
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) {
                ResultadoApi.Error("No se encontraron resultados para '$consulta'")
            } else {
                ResultadoApi.Error("Error del servidor: ${e.code()}")
            }
        } catch (e: Exception) {
            ResultadoApi.Error("Error de conexión: ${e.message}")
        }
    }

    // Endpoint disponible para obtener información detallada por nombre de medicamento
    suspend fun obtenerEtiquetaMedicamento(nombre: String): ResultadoApi<List<EtiquetaDto>> {
        return try {
            val respuesta = cliente.api.obtenerEtiquetaMedicamento("openfda.brand_name:$nombre")
            if (respuesta.resultados != null) {
                ResultadoApi.Exito(respuesta.resultados)
            } else {
                ResultadoApi.Error("No se encontró información del medicamento")
            }
        } catch (e: Exception) {
            ResultadoApi.Error("Error de conexión: ${e.message}")
        }
    }
}