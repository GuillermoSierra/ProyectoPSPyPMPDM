package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Cita
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {

    @Query("SELECT * FROM citas WHERE userId = :userId ORDER BY id DESC")
    fun obtenerTodasCitas(userId: String): Flow<List<Cita>>

    @Query("SELECT * FROM citas WHERE id = :id")
    fun obtenerCitaPorId(id: Int): Flow<Cita?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cita: Cita)

    @Update
    suspend fun actualizar(cita: Cita)

    @Delete
    suspend fun eliminar(cita: Cita)
}