package com.guillermo.healthcare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.guillermo.healthcare.ui.screens.HomeScreen
import com.guillermo.healthcare.ui.screens.MedicationDetailScreen
import com.guillermo.healthcare.ui.screens.MedicationFormScreen
import com.guillermo.healthcare.ui.screens.MedicationListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.MedicationList.route) {
            MedicationListScreen(navController = navController)
        }

        composable(
            route = Screen.MedicationDetail.route,
            arguments = listOf(
                navArgument("medicationId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val medicationId = backStackEntry.arguments?.getInt("medicationId") ?: 0
            MedicationDetailScreen(
                medicationId = medicationId,
                navController = navController
            )
        }

        composable(
            route = Screen.MedicationForm.route,
            arguments = listOf(
                navArgument("medicationId") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val medicationId = backStackEntry.arguments?.getInt("medicationId") ?: -1
            MedicationFormScreen(
                medicationId = if (medicationId == -1) null else medicationId,
                navController = navController
            )
        }
    }
}