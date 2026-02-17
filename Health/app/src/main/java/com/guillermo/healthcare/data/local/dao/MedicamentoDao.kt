package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Medicamento
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicamentoDao {

    @Query("SELECT * FROM medicamentos WHERE userId = :userId ORDER BY id DESC")
    fun obtenerTodosMedicamentos(userId: String): Flow<List<Medicamento>>

    @Query("SELECT * FROM medicamentos WHERE id = :id")
    fun obtenerMedicamentoPorId(id: Int): Flow<Medicamento?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicamento: Medicamento)

    @Update
    suspend fun actualizar(medicamento: Medicamento)

    @Delete
    suspend fun eliminar(medicamento: Medicamento)
}