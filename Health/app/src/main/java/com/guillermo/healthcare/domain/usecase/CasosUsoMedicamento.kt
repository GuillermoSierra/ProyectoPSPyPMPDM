package com.guillermo.healthcare.domain.usecase

import com.guillermo.healthcare.data.local.entity.Medicamento
import com.guillermo.healthcare.data.repository.RepositorioMedicamento
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerMedicamentosUseCase @Inject constructor(
    private val repositorio: RepositorioMedicamento
) {
    operator fun invoke(): Flow<List<Medicamento>> =
        repositorio.obtenerTodosMedicamentos()
}

class ObtenerMedicamentoPorIdUseCase @Inject constructor(
    private val repositorio: RepositorioMedicamento
) {
    operator fun invoke(id: Int): Flow<Medicamento?> =
        repositorio.obtenerMedicamentoPorId(id)
}

class InsertarMedicamentoUseCase @Inject constructor(
    private val repositorio: RepositorioMedicamento
) {
    suspend operator fun invoke(medicamento: Medicamento) =
        repositorio.insertarMedicamento(medicamento)
}

class ActualizarMedicamentoUseCase @Inject constructor(
    private val repositorio: RepositorioMedicamento
) {
    suspend operator fun invoke(medicamento: Medicamento) =
        repositorio.actualizarMedicamento(medicamento)
}

class EliminarMedicamentoUseCase @Inject constructor(
    private val repositorio: RepositorioMedicamento
) {
    suspend operator fun invoke(medicamento: Medicamento) =
        repositorio.eliminarMedicamento(medicamento)
}