package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.MedicationDao
import com.guillermo.healthcare.data.local.entity.Medication
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicationRepository @Inject constructor(
    private val medicationDao: MedicationDao
) {
    fun getAllMedications(): Flow<List<Medication>> {
        return medicationDao.getAllMedications()
    }

    fun getMedicationById(id: Int): Flow<Medication?> {
        return medicationDao.getMedicationById(id)
    }

    fun searchMedications(query: String): Flow<List<Medication>> {
        return medicationDao.searchMedications(query)
    }

    suspend fun insertMedication(medication: Medication) {
        medicationDao.insert(medication)
    }

    suspend fun updateMedication(medication: Medication) {
        medicationDao.update(medication)
    }

    suspend fun deleteMedication(medication: Medication) {
        medicationDao.delete(medication)
    }
}