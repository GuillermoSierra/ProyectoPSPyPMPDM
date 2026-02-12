package com.guillermo.healthcare.ui.screens.medicamentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Medicamento
import com.guillermo.healthcare.data.repository.RepositorioMedicamento
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMedicamento @Inject constructor(
    private val repositorio: RepositorioMedicamento
) : ViewModel() {

    private val _medicamentos = MutableStateFlow<List<Medicamento>>(emptyList())
    val medicamentos: StateFlow<List<Medicamento>> = _medicamentos.asStateFlow()

    private val _medicamentoSeleccionado = MutableStateFlow<Medicamento?>(null)
    val medicamentoSeleccionado: StateFlow<Medicamento?> = _medicamentoSeleccionado.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        cargarMedicamentos()
    }

    private fun cargarMedicamentos() {
        viewModelScope.launch {
            repositorio.obtenerTodosMedicamentos().collect { lista ->
                _medicamentos.value = lista
            }
        }
    }

    fun cargarMedicamentoPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerMedicamentoPorId(id).collect { medicamento ->
                _medicamentoSeleccionado.value = medicamento
            }
        }
    }

    fun insertarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.insertarMedicamento(medicamento)
            _cargando.value = false
        }
    }

    fun actualizarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.actualizarMedicamento(medicamento)
            _cargando.value = false
        }
    }

    fun eliminarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.eliminarMedicamento(medicamento)
            _cargando.value = false
        }
    }

    fun buscarMedicamentos(consulta: String) {
        viewModelScope.launch {
            repositorio.buscarMedicamentos(consulta).collect { lista ->
                _medicamentos.value = lista
            }
        }
    }
}