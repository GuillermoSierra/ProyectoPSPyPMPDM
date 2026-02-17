package com.guillermo.healthcare.data.repository

import com.guillermo.healthcare.data.local.dao.SintomaDao
import com.guillermo.healthcare.data.local.entity.Sintoma
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositorioSintoma @Inject constructor(
    private val sintomaDao: SintomaDao
) {
    fun obtenerTodosSintomas(userId: String): Flow<List<Sintoma>> =
        sintomaDao.obtenerTodosSintomas(userId)

    fun obtenerSintomaPorId(id: Int): Flow<Sintoma?> =
        sintomaDao.obtenerSintomaPorId(id)

    suspend fun insertarSintoma(sintoma: Sintoma) =
        sintomaDao.insertar(sintoma)

    suspend fun actualizarSintoma(sintoma: Sintoma) =
        sintomaDao.actualizar(sintoma)

    suspend fun eliminarSintoma(sintoma: Sintoma) =
        sintomaDao.eliminar(sintoma)
}