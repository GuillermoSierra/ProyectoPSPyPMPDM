package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val nombreDoctor: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val lugar: String,
    val notas: String?
)