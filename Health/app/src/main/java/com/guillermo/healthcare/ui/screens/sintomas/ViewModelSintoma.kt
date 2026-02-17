package com.guillermo.healthcare.ui.screens.sintomas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guillermo.healthcare.data.local.entity.Sintoma
import com.guillermo.healthcare.data.repository.RepositorioSintoma
import com.guillermo.healthcare.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSintoma @Inject constructor(
    private val obtenerSintomas: ObtenerSintomasUseCase,
    private val insertarSintoma: InsertarSintomaUseCase,
    private val actualizarSintoma: ActualizarSintomaUseCase,
    private val eliminarSintoma: EliminarSintomaUseCase,
    private val repositorio: RepositorioSintoma
) : ViewModel() {

    private val _sintomas = MutableStateFlow<List<Sintoma>>(emptyList())
    val sintomas: StateFlow<List<Sintoma>> = _sintomas.asStateFlow()

    private val _sintomaSeleccionado = MutableStateFlow<Sintoma?>(null)
    val sintomaSeleccionado: StateFlow<Sintoma?> = _sintomaSeleccionado.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun cargarSintomas(userId: String) {
        viewModelScope.launch {
            obtenerSintomas(userId).collect { lista ->
                _sintomas.value = lista
            }
        }
    }

    fun cargarSintomaPorId(id: Int) {
        viewModelScope.launch {
            repositorio.obtenerSintomaPorId(id).collect {
                _sintomaSeleccionado.value = it
            }
        }
    }

    fun insertarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            _cargando.value = true
            insertarSintoma.invoke(sintoma)
            _cargando.value = false
        }
    }

    fun actualizarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            _cargando.value = true
            actualizarSintoma.invoke(sintoma)
            _cargando.value = false
        }
    }

    fun eliminarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            _cargando.value = true
            eliminarSintoma.invoke(sintoma)
            _cargando.value = false
        }
    }
}