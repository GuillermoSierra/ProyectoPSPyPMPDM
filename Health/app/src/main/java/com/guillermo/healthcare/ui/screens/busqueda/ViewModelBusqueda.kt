package com.guillermo.healthcare.ui.screens.busqueda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.remote.dto.MedicamentoDto
import com.guillermo.healthcare.data.repository.RepositorioOpenFDA
import com.guillermo.healthcare.data.repository.ResultadoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EstadoBusqueda(
    val cargando: Boolean = false,
    val resultados: List<MedicamentoDto> = emptyList(),
    val error: String? = null,
    val consulta: String = ""
)

@HiltViewModel
class ViewModelBusqueda @Inject constructor(
    private val repositorio: RepositorioOpenFDA
) : ViewModel() {

    private val _estado = MutableStateFlow(EstadoBusqueda())
    val estado: StateFlow<EstadoBusqueda> = _estado.asStateFlow()

    fun actualizarConsulta(consulta: String) {
        _estado.value = _estado.value.copy(consulta = consulta)
    }

    fun buscarMedicamentos() {
        val consulta = _estado.value.consulta
        if (consulta.isBlank()) return

        viewModelScope.launch {
            _estado.value = _estado.value.copy(
                cargando = true,
                error = null,
                resultados = emptyList()
            )

            when (val resultado = repositorio.buscarMedicamentos(consulta)) {
                is ResultadoApi.Exito -> {
                    _estado.value = _estado.value.copy(
                        cargando = false,
                        resultados = resultado.datos
                    )
                }
                is ResultadoApi.Error -> {
                    _estado.value = _estado.value.copy(
                        cargando = false,
                        error = resultado.mensaje
                    )
                }
                is ResultadoApi.Cargando -> {
                    _estado.value = _estado.value.copy(cargando = true)
                }
            }
        }
    }

    fun limpiarBusqueda() {
        _estado.value = EstadoBusqueda()
    }
}