package com.healthcare.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad Room para medicamentos
 * Representa un medicamento que el usuario debe tomar
 */
@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,              // Nombre del medicamento (ej: "Paracetamol")
    val dosage: String,            // Dosis (ej: "500mg")
    val frequency: String,         // Frecuencia (ej: "Cada 8 horas")
    val startDate: Long,           // Fecha de inicio (timestamp)
    val endDate: Long? = null,     // Fecha de fin (opcional)
    val notes: String? = null,     // Notas adicionales
    val isActive: Boolean = true,  // Si está activo actualmente
    val reminderEnabled: Boolean = false, // Si tiene recordatorio
    val createdAt: Long = System.currentTimeMillis()
)