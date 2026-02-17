package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sintomas")
data class Sintoma(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val nombre: String,
    val intensidad: Int,
    val fecha: String,
    val hora: String,
    val descripcion: String?,
    val desencadenantes: String?
)