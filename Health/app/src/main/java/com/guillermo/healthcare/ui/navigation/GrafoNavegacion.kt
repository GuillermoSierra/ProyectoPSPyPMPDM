package com.guillermo.healthcare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.guillermo.healthcare.ui.screens.login.PantallaLogin
import com.guillermo.healthcare.ui.screens.login.ViewModelAuth
import com.guillermo.healthcare.ui.screens.inicio.PantallaInicio
import com.guillermo.healthcare.ui.screens.medicamentos.PantallaListaMedicamentos
import com.guillermo.healthcare.ui.screens.medicamentos.PantallaDetalleMedicamento
import com.guillermo.healthcare.ui.screens.medicamentos.PantallaFormularioMedicamento
import com.guillermo.healthcare.ui.screens.citas.PantallaListaCitas
import com.guillermo.healthcare.ui.screens.citas.PantallaDetalleCita
import com.guillermo.healthcare.ui.screens.citas.PantallaFormularioCita
import com.guillermo.healthcare.ui.screens.sintomas.PantallaListaSintomas
import com.guillermo.healthcare.ui.screens.sintomas.PantallaDetalleSintoma
import com.guillermo.healthcare.ui.screens.sintomas.PantallaFormularioSintoma
import com.guillermo.healthcare.ui.screens.doctores.PantallaListaDoctores
import com.guillermo.healthcare.ui.screens.doctores.PantallaDetalleDoctor
import com.guillermo.healthcare.ui.screens.doctores.PantallaFormularioDoctor
import com.guillermo.healthcare.ui.screens.busqueda.PantallaBusqueda
import com.guillermo.healthcare.ui.screens.perfil.PantallaPerfil

@Composable
fun GrafoNavegacion(
    navController: NavHostController,
    viewModelAuth: ViewModelAuth,
    destinoInicial: String = Pantalla.Login.ruta
) {
    val estadoAuth by viewModelAuth.estado.collectAsState()
    val userId = estadoAuth.userId

    NavHost(
        navController = navController,
        startDestination = destinoInicial
    ) {
        composable(route = Pantalla.Login.ruta) {
            PantallaLogin(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }

        composable(route = Pantalla.Inicio.ruta) {
            PantallaInicio(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }

        composable(route = Pantalla.Perfil.ruta) {
            PantallaPerfil(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }

        // Medicamentos
        composable(route = Pantalla.ListaMedicamentos.ruta) {
            PantallaListaMedicamentos(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }

        composable(
            route = Pantalla.DetalleMedicamento.ruta,
            arguments = listOf(navArgument("medicamentoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("medicamentoId") ?: 0
            PantallaDetalleMedicamento(medicamentoId = id, navController = navController)
        }

        composable(
            route = Pantalla.FormularioMedicamento.ruta,
            arguments = listOf(
                navArgument("medicamentoId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("nombre") {  // ← AÑADIR
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("medicamentoId") ?: -1
            val nombrePrecargado = backStackEntry.arguments?.getString("nombre")
            PantallaFormularioMedicamento(
                medicamentoId = if (id == -1) null else id,
                userId = userId,
                navController = navController,
                nombrePrecargado = nombrePrecargado
            )
        }

        // Citas
        composable(route = Pantalla.ListaCitas.ruta) {
            PantallaListaCitas(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }
        composable(
            route = Pantalla.DetalleCita.ruta,
            arguments = listOf(navArgument("citaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("citaId") ?: 0
            PantallaDetalleCita(citaId = id, navController = navController)
        }
        composable(
            route = Pantalla.FormularioCita.ruta,
            arguments = listOf(navArgument("citaId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("citaId") ?: -1
            PantallaFormularioCita(
                citaId = if (id == -1) null else id,
                userId = userId,
                navController = navController
            )
        }

        // Síntomas
        composable(route = Pantalla.ListaSintomas.ruta) {
            PantallaListaSintomas(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }
        composable(
            route = Pantalla.DetalleSintoma.ruta,
            arguments = listOf(navArgument("sintomaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("sintomaId") ?: 0
            PantallaDetalleSintoma(sintomaId = id, navController = navController)
        }
        composable(
            route = Pantalla.FormularioSintoma.ruta,
            arguments = listOf(navArgument("sintomaId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("sintomaId") ?: -1
            PantallaFormularioSintoma(
                sintomaId = if (id == -1) null else id,
                userId = userId,
                navController = navController
            )
        }

        // Doctores
        composable(route = Pantalla.ListaDoctores.ruta) {
            PantallaListaDoctores(
                navController = navController,
                viewModelAuth = viewModelAuth
            )
        }
        composable(
            route = Pantalla.DetalleDoctor.ruta,
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("doctorId") ?: 0
            PantallaDetalleDoctor(doctorId = id, navController = navController)
        }
        composable(
            route = Pantalla.FormularioDoctor.ruta,
            arguments = listOf(navArgument("doctorId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("doctorId") ?: -1
            PantallaFormularioDoctor(
                doctorId = if (id == -1) null else id,
                userId = userId,
                navController = navController
            )
        }

        composable(route = Pantalla.Busqueda.ruta) {
            PantallaBusqueda(navController = navController)
        }
    }
}