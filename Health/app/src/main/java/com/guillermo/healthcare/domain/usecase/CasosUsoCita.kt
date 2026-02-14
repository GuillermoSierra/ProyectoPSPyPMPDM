package com.guillermo.healthcare.domain.usecase

import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.data.repository.RepositorioCita
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerCitasUseCase @Inject constructor(
    private val repositorio: RepositorioCita
) {
    operator fun invoke(): Flow<List<Cita>> =
        repositorio.obtenerTodasCitas()
}

class InsertarCitaUseCase @Inject constructor(
    private val repositorio: RepositorioCita
) {
    suspend operator fun invoke(cita: Cita) =
        repositorio.insertarCita(cita)
}

class ActualizarCitaUseCase @Inject constructor(
    private val repositorio: RepositorioCita
) {
    suspend operator fun invoke(cita: Cita) =
        repositorio.actualizarCita(cita)
}

class EliminarCitaUseCase @Inject constructor(
    private val repositorio: RepositorioCita
) {
    suspend operator fun invoke(cita: Cita) =
        repositorio.eliminarCita(cita)
}