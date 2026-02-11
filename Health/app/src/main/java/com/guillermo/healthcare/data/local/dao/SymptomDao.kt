package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Symptom
import kotlinx.coroutines.flow.Flow

@Dao
interface SymptomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(symptom: Symptom)

    @Update
    suspend fun update(symptom: Symptom)

    @Delete
    suspend fun delete(symptom: Symptom)

    @Query("SELECT * FROM symptoms ORDER BY date DESC, time DESC")
    fun getAllSymptoms(): Flow<List<Symptom>>

    @Query("SELECT * FROM symptoms WHERE id = :id")
    fun getSymptomById(id: Int): Flow<Symptom?>

    @Query("SELECT * FROM symptoms WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchSymptoms(searchQuery: String): Flow<List<Symptom>>
}