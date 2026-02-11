package com.guillermo.healthcare.data.local.dao

import androidx.room.*
import com.guillermo.healthcare.data.local.entity.Medicamento
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicamentoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicamento: Medicamento)

    @Update
    suspend fun actualizar(medicamento: Medicamento)

    @Delete
    suspend fun eliminar(medicamento: Medicamento)

    @Query("SELECT * FROM medicamentos ORDER BY fechaInicio DESC")
    fun obtenerTodosMedicamentos(): Flow<List<Medicamento>>

    @Query("SELECT * FROM medicamentos WHERE id = :id")
    fun obtenerMedicamentoPorId(id: Int): Flow<Medicamento?>

    @Query("SELECT * FROM medicamentos WHERE nombre LIKE '%' || :busqueda || '%'")
    fun buscarMedicamentos(busqueda: String): Flow<List<Medicamento>>
}