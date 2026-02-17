package com.guillermo.healthcare.ui.screens.citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.data.repository.RepositorioCita
import com.guillermo.healthcare.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelCita @Inject constructor(
    private val obtenerCitas: ObtenerCitasUseCase,
    private val insertarCita: InsertarCitaUseCase,
    private val actualizarCita: ActualizarCitaUseCase,
    private val eliminarCita: EliminarCitaUseCase,
    private val repositorio: RepositorioCita
) : ViewModel() {

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas.asStateFlow()

    private val _citaSeleccionada = MutableStateFlow<Cita?>(null)
    val citaSeleccionada: StateFlow<Cita?> = _citaSeleccionada.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun cargarCitas(userId: String) {
        viewModelScope.launch {
            obtenerCitas(userId).collect { lista ->
                _citas.value = lista
            }
        }
    }

    fun cargarCitaPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerCitaPorId(id).collect {
                _citaSeleccionada.value = it
            }
        }
    }

    fun insertarCita(cita: Cita) {
        viewModelScope.launch {
            _cargando.value = true
            insertarCita.invoke(cita)
            _cargando.value = false
        }
    }

    fun actualizarCita(cita: Cita) {
        viewModelScope.launch {
            _cargando.value = true
            actualizarCita.invoke(cita)
            _cargando.value = false
        }
    }

    fun eliminarCita(cita: Cita) {
        viewModelScope.launch {
            _cargando.value = true
            eliminarCita.invoke(cita)
            _cargando.value = false
        }
    }
}