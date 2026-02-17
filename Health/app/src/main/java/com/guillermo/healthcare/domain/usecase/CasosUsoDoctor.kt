package com.guillermo.healthcare.domain.usecase

import com.guillermo.healthcare.data.local.entity.Doctor
import com.guillermo.healthcare.data.repository.RepositorioDoctor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerDoctoresUseCase @Inject constructor(
    private val repositorio: RepositorioDoctor
) {
    operator fun invoke(userId: String): Flow<List<Doctor>> =
        repositorio.obtenerTodosDoctores(userId)
}

class InsertarDoctorUseCase @Inject constructor(
    private val repositorio: RepositorioDoctor
) {
    suspend operator fun invoke(doctor: Doctor) =
        repositorio.insertarDoctor(doctor)
}

class ActualizarDoctorUseCase @Inject constructor(
    private val repositorio: RepositorioDoctor
) {
    suspend operator fun invoke(doctor: Doctor) =
        repositorio.actualizarDoctor(doctor)
}

class EliminarDoctorUseCase @Inject constructor(
    private val repositorio: RepositorioDoctor
) {
    suspend operator fun invoke(doctor: Doctor) =
        repositorio.eliminarDoctor(doctor)
}