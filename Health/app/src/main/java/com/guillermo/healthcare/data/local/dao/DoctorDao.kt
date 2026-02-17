package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Doctor
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {

    @Query("SELECT * FROM doctores WHERE userId = :userId ORDER BY id DESC")
    fun obtenerTodosDoctores(userId: String): Flow<List<Doctor>>

    @Query("SELECT * FROM doctores WHERE id = :id")
    fun obtenerDoctorPorId(id: Int): Flow<Doctor?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(doctor: Doctor)

    @Update
    suspend fun actualizar(doctor: Doctor)

    @Delete
    suspend fun eliminar(doctor: Doctor)
}