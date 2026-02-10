package com.healthcare.app.data.local.dao

import androidx.room.*
import com.healthcare.app.data.local.entity.MedicationEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones con medicamentos
 * Flow permite observar cambios en tiempo real
 */
@Dao
interface MedicationDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medication: MedicationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medications: List<MedicationEntity>)

    // READ
    @Query("SELECT * FROM medications ORDER BY createdAt DESC")
    fun getAllMedications(): Flow<List<MedicationEntity>>

    @Query("SELECT * FROM medications WHERE id = :medicationId")
    suspend fun getMedicationById(medicationId: Int): MedicationEntity?

    @Query("SELECT * FROM medications WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveMedications(): Flow<List<MedicationEntity>>

    // Búsqueda por nombre (cumple requisito de @Query con búsqueda)
    @Query("SELECT * FROM medications WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchMedications(searchQuery: String): Flow<List<MedicationEntity>>

    // UPDATE
    @Update
    suspend fun update(medication: MedicationEntity)

    // DELETE
    @Delete
    suspend fun delete(medication: MedicationEntity)

    @Query("DELETE FROM medications WHERE id = :medicationId")
    suspend fun deleteById(medicationId: Int)

    @Query("DELETE FROM medications")
    suspend fun deleteAll()
}