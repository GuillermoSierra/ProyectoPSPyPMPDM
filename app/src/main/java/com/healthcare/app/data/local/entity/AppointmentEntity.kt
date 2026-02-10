package com.healthcare.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad Room para citas médicas
 * Representa una cita con un profesional de la salud
 */
@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val doctorName: String,        // Nombre del médico
    val specialty: String,         // Especialidad (ej: "Cardiología")
    val date: Long,                // Fecha de la cita (timestamp)
    val time: String,              // Hora (ej: "10:30")
    val location: String,          // Ubicación (ej: "Hospital Central")
    val reason: String,            // Motivo de la consulta
    val notes: String? = null,     // Notas adicionales
    val status: String = "PENDING", // Estado: PENDING, COMPLETED, CANCELLED
    val reminderEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)