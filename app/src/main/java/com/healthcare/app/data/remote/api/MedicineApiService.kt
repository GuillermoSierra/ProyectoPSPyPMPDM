package com.healthcare.app.data.remote.api

import com.healthcare.app.data.remote.dto.DrugSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Servicio Retrofit para OpenFDA Drug API
 * Documentación: https://open.fda.gov/apis/drug/label/
 */
interface MedicineApiService {

    /**
     * Buscar medicamentos por nombre
     * @param searchQuery nombre del medicamento a buscar
     * @param limit número máximo de resultados (default: 5)
     *
     * Ejemplo: GET /drug/label.json?search=brand_name:tylenol&limit=5
     */
    @GET("drug/label.json")
    suspend fun searchDrugs(
        @Query("search") searchQuery: String,
        @Query("limit") limit: Int = 5
    ): Response<DrugSearchResponse>

    /**
     * Buscar información de un medicamento específico por nombre genérico
     */
    @GET("drug/label.json")
    suspend fun searchByGenericName(
        @Query("search") genericName: String,
        @Query("limit") limit: Int = 1
    ): Response<DrugSearchResponse>
}