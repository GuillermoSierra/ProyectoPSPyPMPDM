package com.healthcare.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthcare.app.data.local.entity.MedicationEntity
import com.healthcare.app.data.remote.dto.MedicineInfo
import com.healthcare.app.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estados para la UI de medicamentos
 */
sealed class MedicationUiState {
    object Loading : MedicationUiState()
    data class Success(val medications: List<MedicationEntity>) : MedicationUiState()
    data class Error(val message: String) : MedicationUiState()
}

/**
 * Estados para búsqueda de API
 */
sealed class ApiSearchState {
    object Idle : ApiSearchState()
    object Loading : ApiSearchState()
    data class Success(val medicines: List<MedicineInfo>) : ApiSearchState()
    data class Error(val message: String) : ApiSearchState()
}

/**
 * ViewModel para gestión de medicamentos
 */
@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    // Estado de la lista de medicamentos
    private val _uiState = MutableStateFlow<MedicationUiState>(MedicationUiState.Loading)
    val uiState: StateFlow<MedicationUiState> = _uiState.asStateFlow()

    // Estado de búsqueda en API
    private val _apiSearchState = MutableStateFlow<ApiSearchState>(ApiSearchState.Idle)
    val apiSearchState: StateFlow<ApiSearchState> = _apiSearchState.asStateFlow()

    // Query de búsqueda local
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadMedications()
    }

    /**
     * Cargar medicamentos desde Room
     */
    private fun loadMedications() {
        viewModelScope.launch {
            repository.getAllMedications()
                .catch { e ->
                    _uiState.value = MedicationUiState.Error(e.message ?: "Error al cargar")
                }
                .collect { medications ->
                    _uiState.value = MedicationUiState.Success(medications)
                }
        }
    }

    /**
     * Buscar medicamentos localmente
     */
    fun searchMedications(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                loadMedications()
            } else {
                repository.searchMedications(query)
                    .catch { e ->
                        _uiState.value = MedicationUiState.Error(e.message ?: "Error en búsqueda")
                    }
                    .collect { medications ->
                        _uiState.value = MedicationUiState.Success(medications)
                    }
            }
        }
    }

    /**
     * Buscar información de medicamento en API
     */
    fun searchMedicineInfo(query: String) {
        viewModelScope.launch {
            _apiSearchState.value = ApiSearchState.Loading

            val result = repository.searchMedicineInfo(query)

            result.fold(
                onSuccess = { medicines ->
                    _apiSearchState.value = ApiSearchState.Success(medicines)
                },
                onFailure = { error ->
                    _apiSearchState.value = ApiSearchState.Error(
                        error.message ?: "Error al buscar medicamento"
                    )
                }
            )
        }
    }

    /**
     * Insertar nuevo medicamento
     */
    fun insertMedication(medication: MedicationEntity) {
        viewModelScope.launch {
            try {
                repository.insertMedication(medication)
            } catch (e: Exception) {
                _uiState.value = MedicationUiState.Error(e.message ?: "Error al guardar")
            }
        }
    }

    /**
     * Actualizar medicamento
     */
    fun updateMedication(medication: MedicationEntity) {
        viewModelScope.launch {
            try {
                repository.updateMedication(medication)
            } catch (e: Exception) {
                _uiState.value = MedicationUiState.Error(e.message ?: "Error al actualizar")
            }
        }
    }

    /**
     * Eliminar medicamento
     */
    fun deleteMedication(medication: MedicationEntity) {
        viewModelScope.launch {
            try {
                repository.deleteMedication(medication)
            } catch (e: Exception) {
                _uiState.value = MedicationUiState.Error(e.message ?: "Error al eliminar")
            }
        }
    }

    /**
     * Resetear estado de búsqueda API
     */
    fun resetApiSearch() {
        _apiSearchState.value = ApiSearchState.Idle
    }
}