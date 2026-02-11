package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sintomas")
data class Sintoma(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,             // Ej: "Dolor de cabeza"
    val intensidad: Int,            // Del 1 al 10
    val fecha: String,              // Ej: "2026-02-11"
    val hora: String,               // Ej: "14:30"
    val descripcion: String?,       // Descripción detallada
    val desencadenantes: String?    // Ej: "Estrés, falta de sueño"
)