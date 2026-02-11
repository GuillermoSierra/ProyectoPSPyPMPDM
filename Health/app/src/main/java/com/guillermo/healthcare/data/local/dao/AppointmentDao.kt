package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment)

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("SELECT * FROM appointments ORDER BY date DESC, time DESC")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE id = :id")
    fun getAppointmentById(id: Int): Flow<Appointment?>

    @Query("SELECT * FROM appointments WHERE doctorName LIKE '%' || :searchQuery || '%' OR specialty LIKE '%' || :searchQuery || '%'")
    fun searchAppointments(searchQuery: String): Flow<List<Appointment>>
}