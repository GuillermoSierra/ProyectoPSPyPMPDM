package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctores")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val nombre: String,
    val especialidad: String,
    val telefono: String,
    val email: String?,
    val direccion: String,
    val notas: String?
)