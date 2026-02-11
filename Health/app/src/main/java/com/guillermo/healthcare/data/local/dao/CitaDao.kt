package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Cita
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cita: Cita)

    @Update
    suspend fun actualizar(cita: Cita)

    @Delete
    suspend fun eliminar(cita: Cita)

    @Query("SELECT * FROM citas ORDER BY fecha DESC, hora DESC")
    fun obtenerTodasCitas(): Flow<List<Cita>>

    @Query("SELECT * FROM citas WHERE id = :id")
    fun obtenerCitaPorId(id: Int): Flow<Cita?>

    @Query("SELECT * FROM citas WHERE nombreDoctor LIKE '%' || :busqueda || '%' OR especialidad LIKE '%' || :busqueda || '%'")
    fun buscarCitas(busqueda: String): Flow<List<Cita>>
}