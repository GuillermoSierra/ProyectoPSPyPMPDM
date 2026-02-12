package com.guillermo.healthcare.ui.screens.sintomas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Sintoma
import com.guillermo.healthcare.data.repository.RepositorioSintoma
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSintoma @Inject constructor(
    private val repositorio: RepositorioSintoma
) : ViewModel() {

    private val _sintomas = MutableStateFlow<List<Sintoma>>(emptyList())
    val sintomas: StateFlow<List<Sintoma>> = _sintomas.asStateFlow()

    private val _sintomaSeleccionado = MutableStateFlow<Sintoma?>(null)
    val sintomaSeleccionado: StateFlow<Sintoma?> = _sintomaSeleccionado.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        cargarSintomas()
    }

    private fun cargarSintomas() {
        viewModelScope.launch {
            repositorio.obtenerTodosSintomas().collect { lista ->
                _sintomas.value = lista
            }
        }
    }

    fun cargarSintomaPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerSintomaPorId(id).collect { sintoma ->
                _sintomaSeleccionado.value = sintoma
            }
        }
    }

    fun insertarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.insertarSintoma(sintoma)
            _cargando.value = false
        }
    }

    fun actualizarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.actualizarSintoma(sintoma)
            _cargando.value = false
        }
    }

    fun eliminarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            _cargando.value = true
            repositorio.eliminarSintoma(sintoma)
            _cargando.value = false
        }
    }
}