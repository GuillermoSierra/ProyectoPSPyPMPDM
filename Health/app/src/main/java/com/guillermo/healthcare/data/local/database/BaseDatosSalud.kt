package com.guillermo.healthcare.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guillermo.healthcare.data.local.dao.CitaDao
import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.dao.MedicamentoDao
import com.guillermo.healthcare.data.local.dao.SintomaDao
import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.data.local.entity.Doctor
import com.guillermo.healthcare.data.local.entity.Medicamento
import com.guillermo.healthcare.data.local.entity.Sintoma

@Database(
    entities = [
        Medicamento::class,
        Cita::class,
        Sintoma::class,
        Doctor::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BaseDatosSalud : RoomDatabase() {

    abstract fun medicamentoDao(): MedicamentoDao
    abstract fun citaDao(): CitaDao
    abstract fun sintomaDao(): SintomaDao
    abstract fun doctorDao(): DoctorDao
}