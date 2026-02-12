package com.guillermo.healthcare.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RespuestaOpenFDA(
    @SerializedName("results")
    val resultados: List<MedicamentoDto>?
)

data class MedicamentoDto(
    @SerializedName("brand_name")
    val nombreMarca: List<String>?,
    @SerializedName("generic_name")
    val nombreGenerico: List<String>?,
    @SerializedName("manufacturer_name")
    val fabricante: List<String>?,
    @SerializedName("route")
    val via: List<String>?,
    @SerializedName("dosage_form")
    val formaDosis: List<String>?
)

data class RespuestaEtiqueta(
    @SerializedName("results")
    val resultados: List<EtiquetaDto>?
)

data class EtiquetaDto(
    @SerializedName("openfda")
    val openFda: MedicamentoDto?,
    @SerializedName("purpose")
    val proposito: List<String>?,
    @SerializedName("warnings")
    val advertencias: List<String>?,
    @SerializedName("dosage_and_administration")
    val dosisAdministracion: List<String>?
)