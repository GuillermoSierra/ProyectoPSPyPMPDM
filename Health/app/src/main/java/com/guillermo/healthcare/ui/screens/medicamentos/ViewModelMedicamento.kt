package com.guillermo.healthcare.ui.screens.medicamentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Medicamento
import com.guillermo.healthcare.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMedicamento @Inject constructor(
    private val obtenerMedicamentos: ObtenerMedicamentosUseCase,
    private val obtenerMedicamentoPorId: ObtenerMedicamentoPorIdUseCase,
    private val insertarMedicamento: InsertarMedicamentoUseCase,
    private val actualizarMedicamento: ActualizarMedicamentoUseCase,
    private val eliminarMedicamento: EliminarMedicamentoUseCase
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
            obtenerMedicamentos().collect { lista ->
                _medicamentos.value = lista
            }
        }
    }

    fun cargarMedicamentoPorId(id: Int) {
        viewModelScope.launch {
            obtenerMedicamentoPorId(id).collect { medicamento ->
                _medicamentoSeleccionado.value = medicamento
            }
        }
    }

    fun insertarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            _cargando.value = true
            insertarMedicamento.invoke(medicamento)
            _cargando.value = false
        }
    }

    fun actualizarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            _cargando.value = true
            actualizarMedicamento.invoke(medicamento)
            _cargando.value = false
        }
    }

    fun eliminarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            _cargando.value = true
            eliminarMedicamento.invoke(medicamento)
            _cargando.value = false
        }
    }
}