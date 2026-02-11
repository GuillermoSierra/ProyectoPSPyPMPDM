package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Doctor
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(doctor: Doctor)

    @Update
    suspend fun actualizar(doctor: Doctor)

    @Delete
    suspend fun eliminar(doctor: Doctor)

    @Query("SELECT * FROM doctores ORDER BY nombre ASC")
    fun obtenerTodosDoctores(): Flow<List<Doctor>>

    @Query("SELECT * FROM doctores WHERE id = :id")
    fun obtenerDoctorPorId(id: Int): Flow<Doctor?>

    @Query("SELECT * FROM doctores WHERE nombre LIKE '%' || :busqueda || '%' OR especialidad LIKE '%' || :busqueda || '%'")
    fun buscarDoctores(busqueda: String): Flow<List<Doctor>>
}