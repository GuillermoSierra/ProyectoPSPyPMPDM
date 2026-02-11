package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val specialty: String,      // Ej: "Cardiólogo"
    val phone: String,
    val email: String?,
    val address: String,
    val notes: String?
)