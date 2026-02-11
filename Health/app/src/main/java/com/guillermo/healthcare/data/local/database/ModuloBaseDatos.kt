package com.guillermo.healthcare.data.local.database

import android.content.Context
import androidx.room.Room
import com.guillermo.healthcare.data.local.dao.CitaDao
import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.dao.MedicamentoDao
import com.guillermo.healthcare.data.local.dao.SintomaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloBaseDatos {

    @Provides
    @Singleton
    fun proveerBaseDatos(
        @ApplicationContext contexto: Context
    ): BaseDatosSalud {
        return Room.databaseBuilder(
            contexto,
            BaseDatosSalud::class.java,
            "base_datos_salud"
        ).build()
    }

    @Provides
    fun proveerMedicamentoDao(baseDatos: BaseDatosSalud): MedicamentoDao {
        return baseDatos.medicamentoDao()
    }

    @Provides
    fun proveerCitaDao(baseDatos: BaseDatosSalud): CitaDao {
        return baseDatos.citaDao()
    }

    @Provides
    fun proveerSintomaDao(baseDatos: BaseDatosSalud): SintomaDao {
        return baseDatos.sintomaDao()
    }

    @Provides
    fun proveerDoctorDao(baseDatos: BaseDatosSalud): DoctorDao {
        return baseDatos.doctorDao()
    }
}