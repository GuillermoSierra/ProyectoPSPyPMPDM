package com.guillermo.healthcare.ui.screens.doctores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Doctor
import com.guillermo.healthcare.data.repository.RepositorioDoctor
import com.guillermo.healthcare.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDoctor @Inject constructor(
    private val obtenerDoctores: ObtenerDoctoresUseCase,
    private val insertarDoctor: InsertarDoctorUseCase,
    private val actualizarDoctor: ActualizarDoctorUseCase,
    private val eliminarDoctor: EliminarDoctorUseCase,
    private val repositorio: RepositorioDoctor
) : ViewModel() {

    private val _doctores = MutableStateFlow<List<Doctor>>(emptyList())
    val doctores: StateFlow<List<Doctor>> = _doctores.asStateFlow()

    private val _doctorSeleccionado = MutableStateFlow<Doctor?>(null)
    val doctorSeleccionado: StateFlow<Doctor?> = _doctorSeleccionado.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun cargarDoctores(userId: String) {
        viewModelScope.launch {
            obtenerDoctores(userId).collect { lista ->
                _doctores.value = lista
            }
        }
    }

    fun cargarDoctorPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerDoctorPorId(id).collect {
                _doctorSeleccionado.value = it
            }
        }
    }

    fun insertarDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _cargando.value = true
            insertarDoctor.invoke(doctor)
            _cargando.value = false
        }
    }

    fun actualizarDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _cargando.value = true
            actualizarDoctor.invoke(doctor)
            _cargando.value = false
        }
    }

    fun eliminarDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _cargando.value = true
            eliminarDoctor.invoke(doctor)
            _cargando.value = false
        }
    }
}