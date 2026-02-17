package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.MedicamentoDao
import com.guillermo.healthcare.data.local.entity.Medicamento
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositorioMedicamento @Inject constructor(
    private val medicamentoDao: MedicamentoDao
) {
    fun obtenerTodosMedicamentos(userId: String): Flow<List<Medicamento>> {
        return medicamentoDao.obtenerTodosMedicamentos(userId)
    }

    fun obtenerMedicamentoPorId(id: Int): Flow<Medicamento?> {
        return medicamentoDao.obtenerMedicamentoPorId(id)
    }

    suspend fun insertarMedicamento(medicamento: Medicamento) {
        medicamentoDao.insertar(medicamento)
    }

    suspend fun actualizarMedicamento(medicamento: Medicamento) {
        medicamentoDao.actualizar(medicamento)
    }

    suspend fun eliminarMedicamento(medicamento: Medicamento) {
        medicamentoDao.eliminar(medicamento)
    }
}