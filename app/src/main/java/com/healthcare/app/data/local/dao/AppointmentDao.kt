package com.healthcare.app.data.local.dao

import androidx.room.*
import com.healthcare.app.data.local.entity.AppointmentEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones con citas médicas
 */
@Dao
interface AppointmentDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: AppointmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appointments: List<AppointmentEntity>)

    // READ
    @Query("SELECT * FROM appointments ORDER BY date ASC, time ASC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Query("SELECT * FROM appointments WHERE id = :appointmentId")
    suspend fun getAppointmentById(appointmentId: Int): AppointmentEntity?

    @Query("SELECT * FROM appointments WHERE status = :status ORDER BY date ASC")
    fun getAppointmentsByStatus(status: String): Flow<List<AppointmentEntity>>

    // Búsqueda por doctor o especialidad
    @Query("""
        SELECT * FROM appointments 
        WHERE doctorName LIKE '%' || :query || '%' 
        OR specialty LIKE '%' || :query || '%'
        ORDER BY date ASC
    """)
    fun searchAppointments(query: String): Flow<List<AppointmentEntity>>

    // Citas próximas (futuras)
    @Query("SELECT * FROM appointments WHERE date >= :currentDate AND status = 'PENDING' ORDER BY date ASC LIMIT 5")
    fun getUpcomingAppointments(currentDate: Long): Flow<List<AppointmentEntity>>

    // UPDATE
    @Update
    suspend fun update(appointment: AppointmentEntity)

    // DELETE
    @Delete
    suspend fun delete(appointment: AppointmentEntity)

    @Query("DELETE FROM appointments WHERE id = :appointmentId")
    suspend fun deleteById(appointmentId: Int)

    @Query("DELETE FROM appointments")
    suspend fun deleteAll()
}