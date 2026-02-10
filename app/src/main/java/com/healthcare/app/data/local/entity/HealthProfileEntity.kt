package com.healthcare.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad Room para el perfil de salud del usuario
 * Solo debe existir UN perfil por usuario
 */
@Entity(tableName = "health_profile")
data class HealthProfileEntity(
    @PrimaryKey
    val userId: String,            // ID del usuario (viene de Auth0)

    val fullName: String,
    val email: String,
    val dateOfBirth: Long? = null, // Fecha de nacimiento (timestamp)
    val bloodType: String? = null, // Grupo sanguíneo (ej: "O+")
    val height: Float? = null,     // Altura en cm
    val weight: Float? = null,     // Peso en kg
    val allergies: String? = null, // Alergias separadas por comas
    val chronicConditions: String? = null, // Condiciones crónicas
    val emergencyContact: String? = null,  // Contacto de emergencia
    val emergencyPhone: String? = null,    // Teléfono de emergencia
    val lastUpdated: Long = System.currentTimeMillis()
)