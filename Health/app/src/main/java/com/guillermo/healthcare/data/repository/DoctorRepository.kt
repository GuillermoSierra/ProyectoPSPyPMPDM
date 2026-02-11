package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.DoctorDao
import com.guillermo.healthcare.data.local.entity.Doctor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    private val doctorDao: DoctorDao
) {
    fun getAllDoctors(): Flow<List<Doctor>> {
        return doctorDao.getAllDoctors()
    }

    fun getDoctorById(id: Int): Flow<Doctor?> {
        return doctorDao.getDoctorById(id)
    }

    fun searchDoctors(query: String): Flow<List<Doctor>> {
        return doctorDao.searchDoctors(query)
    }

    suspend fun insertDoctor(doctor: Doctor) {
        doctorDao.insert(doctor)
    }

    suspend fun updateDoctor(doctor: Doctor) {
        doctorDao.update(doctor)
    }

    suspend fun deleteDoctor(doctor: Doctor) {
        doctorDao.delete(doctor)
    }
}