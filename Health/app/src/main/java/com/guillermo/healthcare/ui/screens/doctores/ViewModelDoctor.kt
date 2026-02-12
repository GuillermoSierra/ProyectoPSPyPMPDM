package com.guillermo.healthcare.ui.screens.doctores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Doctor
import com.guillermo.healthcare.data.repository.RepositorioDoctor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDoctor @Inject constructor(
    private val repositorio: RepositorioDoctor
) : ViewModel() {

    private val _doctores = MutableStateFlow<List<Doctor>>(emptyList())
    val doctores: StateFlow<List<Doctor>> = _doctores.asStateFlow()

    private val _doctorSeleccionado = MutableStateFlow<Doctor?>(null)
    val doctorSeleccionado: StateFlow<Doctor?> = _doctorSeleccionado.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        cargarDoctores()
    }

    private fun cargarDoctores() {
        viewModelScope.launch {
            repositorio.obtenerTodosDoctores().collect { lista ->
                _doctores.value = lista
            }
        }
    }

    fun cargarDoctorPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerDoctorPorId(id).collect { doctor ->
                _doctorSeleccionado.value = doctor
            }
        }
    }

    fun insertarDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.insertarDoctor(doctor)
            _cargando.value = false
        }
    }

    fun actualizarDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.actualizarDoctor(doctor)
            _cargando.value = false
        }
    }

    fun eliminarDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.eliminarDoctor(doctor)
            _cargando.value = false
        }
    }
}