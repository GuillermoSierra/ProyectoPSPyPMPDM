package com.guillermo.healthcare.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val doctorName: String,
    val specialty: String,      // Ej: "Cardiólogo"
    val date: String,           // Ej: "2026-02-15"
    val time: String,           // Ej: "10:30"
    val location: String,       // Ej: "Hospital Central"
    val notes: String?
)