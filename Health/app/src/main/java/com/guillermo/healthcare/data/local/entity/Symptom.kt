package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class Symptom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,           // Ej: "Dolor de cabeza"
    val severity: Int,          // Del 1 al 10
    val date: String,           // Ej: "2026-02-11"
    val time: String,           // Ej: "14:30"
    val description: String?,   // Descripción detallada
    val triggers: String?       // Ej: "Estrés, falta de sueño"
)