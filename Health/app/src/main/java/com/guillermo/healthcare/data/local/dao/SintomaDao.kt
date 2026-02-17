package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Sintoma
import kotlinx.coroutines.flow.Flow

@Dao
interface SintomaDao {

    @Query("SELECT * FROM sintomas WHERE userId = :userId ORDER BY id DESC")
    fun obtenerTodosSintomas(userId: String): Flow<List<Sintoma>>

    @Query("SELECT * FROM sintomas WHERE id = :id")
    fun obtenerSintomaPorId(id: Int): Flow<Sintoma?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(sintoma: Sintoma)

    @Update
    suspend fun actualizar(sintoma: Sintoma)

    @Delete
    suspend fun eliminar(sintoma: Sintoma)
}