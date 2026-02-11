package com.guillermo.healthcare.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guillermo.healthcare.data.local.dao.AppointmentDao
import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.dao.MedicationDao
import com.guillermo.healthcare.data.local.dao.SymptomDao
import com.guillermo.healthcare.data.local.entity.Appointment
import com.guillermo.healthcare.data.local.entity.Doctor
import com.guillermo.healthcare.data.local.entity.Medication
import com.guillermo.healthcare.data.local.entity.Symptom

@Database(
    entities = [
        Medication::class,
        Appointment::class,
        Symptom::class,
        Doctor::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HealthCareDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun symptomDao(): SymptomDao
    abstract fun doctorDao(): DoctorDao
}