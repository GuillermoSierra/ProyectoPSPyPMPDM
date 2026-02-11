package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.AppointmentDao
import com.guillermo.healthcare.data.local.entity.Appointment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppointmentRepository @Inject constructor(
    private val appointmentDao: AppointmentDao
) {
    fun getAllAppointments(): Flow<List<Appointment>> {
        return appointmentDao.getAllAppointments()
    }

    fun getAppointmentById(id: Int): Flow<Appointment?> {
        return appointmentDao.getAppointmentById(id)
    }

    fun searchAppointments(query: String): Flow<List<Appointment>> {
        return appointmentDao.searchAppointments(query)
    }

    suspend fun insertAppointment(appointment: Appointment) {
        appointmentDao.insert(appointment)
    }

    suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.update(appointment)
    }

    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.delete(appointment)
    }
}