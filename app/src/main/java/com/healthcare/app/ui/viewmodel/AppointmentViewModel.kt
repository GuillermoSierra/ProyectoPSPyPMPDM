package com.healthcare.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthcare.app.data.local.entity.AppointmentEntity
import com.healthcare.app.data.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estados para la UI de citas
 */
sealed class AppointmentUiState {
    object Loading : AppointmentUiState()
    data class Success(val appointments: List<AppointmentEntity>) : AppointmentUiState()
    data class Error(val message: String) : AppointmentUiState()
}

/**
 * ViewModel para gestión de citas médicas
 */
@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppointmentUiState>(AppointmentUiState.Loading)
    val uiState: StateFlow<AppointmentUiState> = _uiState.asStateFlow()

    private val _upcomingAppointments = MutableStateFlow<List<AppointmentEntity>>(emptyList())
    val upcomingAppointments: StateFlow<List<AppointmentEntity>> = _upcomingAppointments.asStateFlow()

    init {
        loadAppointments()
        loadUpcomingAppointments()
    }

    /**
     * Cargar todas las citas
     */
    private fun loadAppointments() {
        viewModelScope.launch {
            repository.getAllAppointments()
                .catch { e ->
                    _uiState.value = AppointmentUiState.Error(e.message ?: "Error al cargar")
                }
                .collect { appointments ->
                    _uiState.value = AppointmentUiState.Success(appointments)
                }
        }
    }

    /**
     * Cargar citas próximas
     */
    private fun loadUpcomingAppointments() {
        viewModelScope.launch {
            repository.getUpcomingAppointments()
                .catch { /* silencioso */ }
                .collect { appointments ->
                    _upcomingAppointments.value = appointments
                }
        }
    }

    /**
     * Buscar citas
     */
    fun searchAppointments(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                loadAppointments()
            } else {
                repository.searchAppointments(query)
                    .catch { e ->
                        _uiState.value = AppointmentUiState.Error(e.message ?: "Error en búsqueda")
                    }
                    .collect { appointments ->
                        _uiState.value = AppointmentUiState.Success(appointments)
                    }
            }
        }
    }

    /**
     * Filtrar por estado
     */
    fun filterByStatus(status: String) {
        viewModelScope.launch {
            if (status == "ALL") {
                loadAppointments()
            } else {
                repository.getAppointmentsByStatus(status)
                    .catch { e ->
                        _uiState.value = AppointmentUiState.Error(e.message ?: "Error al filtrar")
                    }
                    .collect { appointments ->
                        _uiState.value = AppointmentUiState.Success(appointments)
                    }
            }
        }
    }

    /**
     * Insertar nueva cita
     */
    fun insertAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            try {
                repository.insertAppointment(appointment)
            } catch (e: Exception) {
                _uiState.value = AppointmentUiState.Error(e.message ?: "Error al guardar")
            }
        }
    }

    /**
     * Actualizar cita
     */
    fun updateAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            try {
                repository.updateAppointment(appointment)
            } catch (e: Exception) {
                _uiState.value = AppointmentUiState.Error(e.message ?: "Error al actualizar")
            }
        }
    }

    /**
     * Eliminar cita
     */
    fun deleteAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            try {
                repository.deleteAppointment(appointment)
            } catch (e: Exception) {
                _uiState.value = AppointmentUiState.Error(e.message ?: "Error al eliminar")
            }
        }
    }

    /**
     * Marcar cita como completada
     */
    fun markAsCompleted(appointmentId: Int) {
        viewModelScope.launch {
            try {
                repository.markAsCompleted(appointmentId)
            } catch (e: Exception) {
                _uiState.value = AppointmentUiState.Error(e.message ?: "Error al completar")
            }
        }
    }

    /**
     * Cancelar cita
     */
    fun cancelAppointment(appointmentId: Int) {
        viewModelScope.launch {
            try {
                repository.cancelAppointment(appointmentId)
            } catch (e: Exception) {
                _uiState.value = AppointmentUiState.Error(e.message ?: "Error al cancelar")
            }
        }
    }
}