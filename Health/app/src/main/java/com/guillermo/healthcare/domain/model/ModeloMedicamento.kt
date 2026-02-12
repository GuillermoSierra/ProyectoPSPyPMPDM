package com.guillermo.healthcare.domain.model

data class ModeloMedicamento(
    val id: Int = 0,
    val nombre: String,
    val dosis: String,
    val frecuencia: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val notas: String?
)