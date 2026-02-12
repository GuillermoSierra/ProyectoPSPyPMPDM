package com.guillermo.healthcare.data.remote.api

import com.guillermo.healthcare.data.remote.dto.RespuestaEtiqueta
import com.guillermo.healthcare.data.remote.dto.RespuestaOpenFDA
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFDAApi {

    @GET("drug/ndc.json")
    suspend fun buscarMedicamentos(
        @Query("search") busqueda: String,
        @Query("limit") limite: Int = 10
    ): RespuestaOpenFDA

    @GET("drug/label.json")
    suspend fun obtenerEtiquetaMedicamento(
        @Query("search") busqueda: String,
        @Query("limit") limite: Int = 5
    ): RespuestaEtiqueta
}