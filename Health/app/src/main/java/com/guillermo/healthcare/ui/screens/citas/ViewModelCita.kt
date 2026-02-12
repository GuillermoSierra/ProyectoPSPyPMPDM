package com.guillermo.healthcare.ui.screens.citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.data.repository.RepositorioCita
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelCita @Inject constructor(
    private val repositorio: RepositorioCita
) : ViewModel() {

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas.asStateFlow()

    private val _citaSeleccionada = MutableStateFlow<Cita?>(null)
    val citaSeleccionada: StateFlow<Cita?> = _citaSeleccionada.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        cargarCitas()
    }

    private fun cargarCitas() {
        viewModelScope.launch {
            repositorio.obtenerTodasCitas().collect { lista ->
                _citas.value = lista
            }
        }
    }

    fun cargarCitaPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerCitaPorId(id).collect { cita ->
                _citaSeleccionada.value = cita
            }
        }
    }

    fun insertarCita(cita: Cita) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.insertarCita(cita)
            _cargando.value = false
        }
    }

    fun actualizarCita(cita: Cita) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.actualizarCita(cita)
            _cargando.value = false
        }
    }

    fun eliminarCita(cita: Cita) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.eliminarCita(cita)
            _cargando.value = false
        }
    }
}