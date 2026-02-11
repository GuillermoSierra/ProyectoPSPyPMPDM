package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.SymptomDao
import com.guillermo.healthcare.data.local.entity.Symptom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SymptomRepository @Inject constructor(
    private val symptomDao: SymptomDao
) {
    fun getAllSymptoms(): Flow<List<Symptom>> {
        return symptomDao.getAllSymptoms()
    }

    fun getSymptomById(id: Int): Flow<Symptom?> {
        return symptomDao.getSymptomById(id)
    }

    fun searchSymptoms(query: String): Flow<List<Symptom>> {
        return symptomDao.searchSymptoms(query)
    }

    suspend fun insertSymptom(symptom: Symptom) {
        symptomDao.insert(symptom)
    }

    suspend fun updateSymptom(symptom: Symptom) {
        symptomDao.update(symptom)
    }

    suspend fun deleteSymptom(symptom: Symptom) {
        symptomDao.delete(symptom)
    }
}