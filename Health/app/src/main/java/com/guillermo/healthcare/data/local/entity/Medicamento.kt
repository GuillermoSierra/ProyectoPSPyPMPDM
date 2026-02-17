package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val nombre: String,
    val dosis: String,
    val frecuencia: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val notas: String?
)