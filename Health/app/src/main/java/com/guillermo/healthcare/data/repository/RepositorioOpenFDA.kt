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
            if (respuesta.resultados != null) {
                ResultadoApi.Exito(respuesta.resultados)
            } else {
                ResultadoApi.Error("No se encontraron resultados")
            }
        } catch (e: Exception) {
            ResultadoApi.Error("Error de conexión: ${e.message}")
        }
    }

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