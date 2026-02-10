package com.healthcare.app.data.repository

import com.healthcare.app.data.local.dao.AppointmentDao
import com.healthcare.app.data.local.entity.AppointmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository para citas médicas
 * Solo usa datos locales (Room)
 */
@Singleton
class AppointmentRepository @Inject constructor(
    private val appointmentDao: AppointmentDao
) {

    /**
     * Obtener todas las citas
     */
    fun getAllAppointments(): Flow<List<AppointmentEntity>> {
        return appointmentDao.getAllAppointments()
    }

    /**
     * Obtener citas próximas
     */
    fun getUpcomingAppointments(): Flow<List<AppointmentEntity>> {
        val currentDate = System.currentTimeMillis()
        return appointmentDao.getUpcomingAppointments(currentDate)
    }

    /**
     * Obtener citas por estado
     */
    fun getAppointmentsByStatus(status: String): Flow<List<AppointmentEntity>> {
        return appointmentDao.getAppointmentsByStatus(status)
    }

    /**
     * Buscar citas por doctor o especialidad
     */
    fun searchAppointments(query: String): Flow<List<AppointmentEntity>> {
        return appointmentDao.searchAppointments(query)
    }

    /**
     * Obtener una cita por ID
     */
    suspend fun getAppointmentById(id: Int): AppointmentEntity? {
        return appointmentDao.getAppointmentById(id)
    }

    /**
     * Insertar una nueva cita
     */
    suspend fun insertAppointment(appointment: AppointmentEntity): Long {
        return appointmentDao.insert(appointment)
    }

    /**
     * Actualizar una cita existente
     */
    suspend fun updateAppointment(appointment: AppointmentEntity) {
        appointmentDao.update(appointment)
    }

    /**
     * Eliminar una cita
     */
    suspend fun deleteAppointment(appointment: AppointmentEntity) {
        appointmentDao.delete(appointment)
    }

    /**
     * Marcar cita como completada
     */
    suspend fun markAsCompleted(appointmentId: Int) {
        val appointment = appointmentDao.getAppointmentById(appointmentId)
        appointment?.let {
            val updated = it.copy(status = "COMPLETED")
            appointmentDao.update(updated)
        }
    }

    /**
     * Cancelar cita
     */
    suspend fun cancelAppointment(appointmentId: Int) {
        val appointment = appointmentDao.getAppointmentById(appointmentId)
        appointment?.let {
            val updated = it.copy(status = "CANCELLED")
            appointmentDao.update(updated)
        }
    }
}