package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.entity.Doctor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositorioDoctor @Inject constructor(
    private val doctorDao: DoctorDao
) {
    fun obtenerTodosDoctores(userId: String): Flow<List<Doctor>> =
        doctorDao.obtenerTodosDoctores(userId)

    fun obtenerDoctorPorId(id: Int): Flow<Doctor?> =
        doctorDao.obtenerDoctorPorId(id)

    suspend fun insertarDoctor(doctor: Doctor) =
        doctorDao.insertar(doctor)

    suspend fun actualizarDoctor(doctor: Doctor) =
        doctorDao.actualizar(doctor)

    suspend fun eliminarDoctor(doctor: Doctor) =
        doctorDao.eliminar(doctor)
}