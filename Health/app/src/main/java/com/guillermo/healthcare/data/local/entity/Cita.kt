package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreDoctor: String,
    val especialidad: String,       // Ej: "Cardiólogo"
    val fecha: String,              // Ej: "2026-02-15"
    val hora: String,               // Ej: "10:30"
    val lugar: String,              // Ej: "Hospital Central"
    val notas: String?
)