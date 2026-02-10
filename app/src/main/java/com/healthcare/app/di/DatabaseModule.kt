package com.healthcare.app.di

import android.content.Context
import androidx.room.Room
import com.healthcare.app.data.local.dao.AppointmentDao
import com.healthcare.app.data.local.dao.HealthProfileDao
import com.healthcare.app.data.local.dao.MedicationDao
import com.healthcare.app.data.local.database.HealthCareDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para inyección de dependencias de la base de datos
 * Provee la instancia de Room Database y sus DAOs
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provee la instancia única de la base de datos
     */
    @Provides
    @Singleton
    fun provideHealthCareDatabase(
        @ApplicationContext context: Context
    ): HealthCareDatabase {
        return Room.databaseBuilder(
            context,
            HealthCareDatabase::class.java,
            HealthCareDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // Solo para desarrollo
            .build()
    }

    /**
     * Provee el DAO de medicamentos
     */
    @Provides
    @Singleton
    fun provideMedicationDao(database: HealthCareDatabase): MedicationDao {
        return database.medicationDao()
    }

    /**
     * Provee el DAO de citas
     */
    @Provides
    @Singleton
    fun provideAppointmentDao(database: HealthCareDatabase): AppointmentDao {
        return database.appointmentDao()
    }

    /**
     * Provee el DAO de perfil de salud
     */
    @Provides
    @Singleton
    fun provideHealthProfileDao(database: HealthCareDatabase): HealthProfileDao {
        return database.healthProfileDao()
    }
}