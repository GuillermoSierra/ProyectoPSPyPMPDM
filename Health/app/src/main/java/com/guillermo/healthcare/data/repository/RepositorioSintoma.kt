package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.SintomaDao
import com.guillermo.healthcare.data.local.entity.Sintoma
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositorioSintoma @Inject constructor(
    private val sintomaDao: SintomaDao
) {
    fun obtenerTodosSintomas(): Flow<List<Sintoma>> {
        return sintomaDao.obtenerTodosSintomas()
    }

    fun obtenerSintomaPorId(id: Int): Flow<Sintoma?> {
        return sintomaDao.obtenerSintomaPorId(id)
    }

    fun buscarSintomas(consulta: String): Flow<List<Sintoma>> {
        return sintomaDao.buscarSintomas(consulta)
    }

    suspend fun insertarSintoma(sintoma: Sintoma) {
        sintomaDao.insertar(sintoma)
    }

    suspend fun actualizarSintoma(sintoma: Sintoma) {
        sintomaDao.actualizar(sintoma)
    }

    suspend fun eliminarSintoma(sintoma: Sintoma) {
        sintomaDao.eliminar(sintoma)
    }
}