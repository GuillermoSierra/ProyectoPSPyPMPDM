package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val dosis: String,              // Ej: "500mg"
    val frecuencia: String,         // Ej: "Cada 8 horas"
    val fechaInicio: String,        // Ej: "2026-02-01"
    val fechaFin: String?,          // Puede ser null si es indefinido
    val notas: String?              // Notas adicionales
)