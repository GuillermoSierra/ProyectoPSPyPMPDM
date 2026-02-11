package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.CitaDao
import com.guillermo.healthcare.data.local.entity.Cita
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositorioCita @Inject constructor(
    private val citaDao: CitaDao
) {
    fun obtenerTodasCitas(): Flow<List<Cita>> {
        return citaDao.obtenerTodasCitas()
    }

    fun obtenerCitaPorId(id: Int): Flow<Cita?> {
        return citaDao.obtenerCitaPorId(id)
    }

    fun buscarCitas(consulta: String): Flow<List<Cita>> {
        return citaDao.buscarCitas(consulta)
    }

    suspend fun insertarCita(cita: Cita) {
        citaDao.insertar(cita)
    }

    suspend fun actualizarCita(cita: Cita) {
        citaDao.actualizar(cita)
    }

    suspend fun eliminarCita(cita: Cita) {
        citaDao.eliminar(cita)
    }
}