package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Doctor
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: Doctor)

    @Update
    suspend fun update(doctor: Doctor)

    @Delete
    suspend fun delete(doctor: Doctor)

    @Query("SELECT * FROM doctors ORDER BY name ASC")
    fun getAllDoctors(): Flow<List<Doctor>>

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getDoctorById(id: Int): Flow<Doctor?>

    @Query("SELECT * FROM doctors WHERE name LIKE '%' || :searchQuery || '%' OR specialty LIKE '%' || :searchQuery || '%'")
    fun searchDoctors(searchQuery: String): Flow<List<Doctor>>
}