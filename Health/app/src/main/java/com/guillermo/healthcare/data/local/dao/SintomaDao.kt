package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Sintoma
import kotlinx.coroutines.flow.Flow

@Dao
interface SintomaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(sintoma: Sintoma)

    @Update
    suspend fun actualizar(sintoma: Sintoma)

    @Delete
    suspend fun eliminar(sintoma: Sintoma)

    @Query("SELECT * FROM sintomas ORDER BY fecha DESC, hora DESC")
    fun obtenerTodosSintomas(): Flow<List<Sintoma>>

    @Query("SELECT * FROM sintomas WHERE id = :id")
    fun obtenerSintomaPorId(id: Int): Flow<Sintoma?>

    @Query("SELECT * FROM sintomas WHERE nombre LIKE '%' || :busqueda || '%'")
    fun buscarSintomas(busqueda: String): Flow<List<Sintoma>>
}