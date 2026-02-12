package com.guillermo.healthcare.domain.model

data class ModeloCita(
    val id: Int = 0,
    val nombreDoctor: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val lugar: String,
    val notas: String?
)