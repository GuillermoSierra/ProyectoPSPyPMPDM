package com.guillermo.healthcare.di

import android.content.Context
import androidx.room.Room
import com.guillermo.healthcare.data.local.dao.AppointmentDao
import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.dao.MedicationDao
import com.guillermo.healthcare.data.local.dao.SymptomDao
import com.guillermo.healthcare.data.local.database.HealthCareDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): HealthCareDatabase {
        return Room.databaseBuilder(
            context,
            HealthCareDatabase::class.java,
            "healthcare_database"
        ).build()
    }

    @Provides
    fun provideMedicationDao(database: HealthCareDatabase): MedicationDao {
        return database.medicationDao()
    }

    @Provides
    fun provideAppointmentDao(database: HealthCareDatabase): AppointmentDao {
        return database.appointmentDao()
    }

    @Provides
    fun provideSymptomDao(database: HealthCareDatabase): SymptomDao {
        return database.symptomDao()
    }

    @Provides
    fun provideDoctorDao(database: HealthCareDatabase): DoctorDao {
        return database.doctorDao()
    }
}