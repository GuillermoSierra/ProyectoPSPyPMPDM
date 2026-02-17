package com.guillermo.healthcare.domain.usecase

import com.guillermo.healthcare.data.local.entity.Sintoma
import com.guillermo.healthcare.data.repository.RepositorioSintoma
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerSintomasUseCase @Inject constructor(
    private val repositorio: RepositorioSintoma
) {
    operator fun invoke(userId: String): Flow<List<Sintoma>> =
        repositorio.obtenerTodosSintomas(userId)
}

class InsertarSintomaUseCase @Inject constructor(
    private val repositorio: RepositorioSintoma
) {
    suspend operator fun invoke(sintoma: Sintoma) =
        repositorio.insertarSintoma(sintoma)
}

class ActualizarSintomaUseCase @Inject constructor(
    private val repositorio: RepositorioSintoma
) {
    suspend operator fun invoke(sintoma: Sintoma) =
        repositorio.actualizarSintoma(sintoma)
}

class EliminarSintomaUseCase @Inject constructor(
    private val repositorio: RepositorioSintoma
) {
    suspend operator fun invoke(sintoma: Sintoma) =
        repositorio.eliminarSintoma(sintoma)
}