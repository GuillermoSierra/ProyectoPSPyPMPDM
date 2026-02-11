package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.entity.Doctor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositorioDoctor @Inject constructor(
    private val doctorDao: DoctorDao
) {
    fun obtenerTodosDoctores(): Flow<List<Doctor>> {
        return doctorDao.obtenerTodosDoctores()
    }

    fun obtenerDoctorPorId(id: Int): Flow<Doctor?> {
        return doctorDao.obtenerDoctorPorId(id)
    }

    fun buscarDoctores(consulta: String): Flow<List<Doctor>> {
        return doctorDao.buscarDoctores(consulta)
    }

    suspend fun insertarDoctor(doctor: Doctor) {
        doctorDao.insertar(doctor)
    }

    suspend fun actualizarDoctor(doctor: Doctor) {
        doctorDao.actualizar(doctor)
    }

    suspend fun eliminarDoctor(doctor: Doctor) {
        doctorDao.eliminar(doctor)
    }
}