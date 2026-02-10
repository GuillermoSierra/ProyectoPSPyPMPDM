package com.healthcare.app.data.local.dao

import androidx.room.*
import com.healthcare.app.data.local.entity.HealthProfileEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para el perfil de salud del usuario
 * Solo debe existir un perfil por usuario
 */
@Dao
interface HealthProfileDao {

    // CREATE/UPDATE (Upsert)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: HealthProfileEntity)

    // READ
    @Query("SELECT * FROM health_profile WHERE userId = :userId")
    fun getProfile(userId: String): Flow<HealthProfileEntity?>

    @Query("SELECT * FROM health_profile WHERE userId = :userId")
    suspend fun getProfileOnce(userId: String): HealthProfileEntity?

    // UPDATE
    @Update
    suspend fun update(profile: HealthProfileEntity)

    // DELETE
    @Delete
    suspend fun delete(profile: HealthProfileEntity)

    @Query("DELETE FROM health_profile WHERE userId = :userId")
    suspend fun deleteByUserId(userId: String)
}