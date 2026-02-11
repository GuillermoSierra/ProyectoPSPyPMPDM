package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dosage: String,        // Ej: "500mg"
    val frequency: String,     // Ej: "Cada 8 horas"
    val startDate: String,     // Ej: "2026-02-01"
    val endDate: String?,      // Puede ser null si es indefinido
    val notes: String?         // Notas adicionales
)