package com.healthcare.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.healthcare.app.data.local.dao.AppointmentDao
import com.healthcare.app.data.local.dao.HealthProfileDao
import com.healthcare.app.data.local.dao.MedicationDao
import com.healthcare.app.data.local.entity.AppointmentEntity
import com.healthcare.app.data.local.entity.HealthProfileEntity
import com.healthcare.app.data.local.entity.MedicationEntity

/**
 * Base de datos principal de la aplicación HealthCare
 * Room Database con 3 entidades principales
 */
@Database(
    entities = [
        MedicationEntity::class,
        AppointmentEntity::class,
        HealthProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HealthCareDatabase : RoomDatabase() {

    // DAOs
    abstract fun medicationDao(): MedicationDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun healthProfileDao(): HealthProfileDao

    companion object {
        const val DATABASE_NAME = "healthcare_db"
    }
}