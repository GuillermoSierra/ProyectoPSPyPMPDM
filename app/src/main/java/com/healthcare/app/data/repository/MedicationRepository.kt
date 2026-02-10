package com.healthcare.app.data.repository

import com.healthcare.app.data.local.dao.MedicationDao
import com.healthcare.app.data.local.entity.MedicationEntity
import com.healthcare.app.data.remote.api.MedicineApiService
import com.healthcare.app.data.remote.dto.MedicineInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository para medicamentos
 * Abstrae las fuentes de datos (local y remota)
 * Implementa el patrón Repository de arquitectura MVVM
 */
@Singleton
class MedicationRepository @Inject constructor(
    private val medicationDao: MedicationDao,
    private val medicineApiService: MedicineApiService
) {

    // ===== OPERACIONES LOCALES (Room) =====

    /**
     * Obtener todos los medicamentos (Flow para observar cambios)
     */
    fun getAllMedications(): Flow<List<MedicationEntity>> {
        return medicationDao.getAllMedications()
    }

    /**
     * Obtener medicamentos activos
     */
    fun getActiveMedications(): Flow<List<MedicationEntity>> {
        return medicationDao.getActiveMedications()
    }

    /**
     * Buscar medicamentos por nombre
     */
    fun searchMedications(query: String): Flow<List<MedicationEntity>> {
        return medicationDao.searchMedications(query)
    }

    /**
     * Obtener un medicamento por ID
     */
    suspend fun getMedicationById(id: Int): MedicationEntity? {
        return medicationDao.getMedicationById(id)
    }

    /**
     * Insertar un nuevo medicamento
     */
    suspend fun insertMedication(medication: MedicationEntity): Long {
        return medicationDao.insert(medication)
    }

    /**
     * Actualizar un medicamento existente
     */
    suspend fun updateMedication(medication: MedicationEntity) {
        medicationDao.update(medication)
    }

    /**
     * Eliminar un medicamento
     */
    suspend fun deleteMedication(medication: MedicationEntity) {
        medicationDao.delete(medication)
    }

    // ===== OPERACIONES REMOTAS (API) =====

    /**
     * Buscar información de medicamentos en OpenFDA
     * Retorna Result para manejar éxito/error
     */
    suspend fun searchMedicineInfo(query: String): Result<List<MedicineInfo>> {
        return try {
            val searchQuery = "brand_name:$query"
            val response = medicineApiService.searchDrugs(searchQuery, limit = 10)

            if (response.isSuccessful) {
                val results = response.body()?.results ?: emptyList()
                val medicines = results.map { MedicineInfo.fromDrugResult(it) }
                Result.success(medicines)
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}