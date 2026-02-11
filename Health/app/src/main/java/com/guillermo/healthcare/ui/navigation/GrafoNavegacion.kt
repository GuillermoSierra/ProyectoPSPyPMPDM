package com.guillermo.healthcare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.guillermo.healthcare.ui.screens.PantallaDetalleMedicamento
import com.guillermo.healthcare.ui.screens.PantallaFormularioMedicamento
import com.guillermo.healthcare.ui.screens.PantallaInicio
import com.guillermo.healthcare.ui.screens.PantallaListaMedicamentos

@Composable
fun GrafoNavegacion(
    navController: NavHostController,
    destinoInicial: String = Pantalla.Inicio.ruta
) {
    NavHost(
        navController = navController,
        startDestination = destinoInicial
    ) {
        composable(route = Pantalla.Inicio.ruta) {
            PantallaInicio(navController = navController)
        }

        composable(route = Pantalla.ListaMedicamentos.ruta) {
            PantallaListaMedicamentos(navController = navController)
        }

        composable(
            route = Pantalla.DetalleMedicamento.ruta,
            arguments = listOf(
                navArgument("medicamentoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val medicamentoId = backStackEntry.arguments?.getInt("medicamentoId") ?: 0
            PantallaDetalleMedicamento(
                medicamentoId = medicamentoId,
                navController = navController
            )
        }

        composable(
            route = Pantalla.FormularioMedicamento.ruta,
            arguments = listOf(
                navArgument("medicamentoId") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val medicamentoId = backStackEntry.arguments?.getInt("medicamentoId") ?: -1
            PantallaFormularioMedicamento(
                medicamentoId = if (medicamentoId == -1) null else medicamentoId,
                navController = navController
            )
        }
    }
}