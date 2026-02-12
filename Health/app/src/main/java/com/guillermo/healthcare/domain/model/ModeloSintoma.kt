package com.guillermo.healthcare.domain.model

data class ModeloSintoma(
    val id: Int = 0,
    val nombre: String,
    val intensidad: Int,
    val fecha: String,
    val hora: String,
    val descripcion: String?,
    val desencadenantes: String?
)