package com.guillermo.healthcare.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 2,
    exportSchema = false
)
abstract class BaseDatosSalud : RoomDatabase() {

    abstract fun medicamentoDao(): MedicamentoDao
    abstract fun citaDao(): CitaDao
    abstract fun sintomaDao(): SintomaDao
    abstract fun doctorDao(): DoctorDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE medicamentos ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE citas ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sintomas ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE doctores ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}