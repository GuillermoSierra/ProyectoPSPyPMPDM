package com.guillermo.healthcare.domain.model

data class ModeloDoctor(
    val id: Int = 0,
    val nombre: String,
    val especialidad: String,
    val telefono: String,
    val email: String?,
    val direccion: String,
    val notas: String?
)