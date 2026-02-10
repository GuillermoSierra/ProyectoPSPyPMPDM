package com.healthcare.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.healthcare.app.ui.screens.*
import com.healthcare.app.ui.viewmodel.AuthViewModel

/**
 * Grafo de navegación principal
 */
@Composable
fun HealthCareNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Home.route else Screen.Login.route
    ) {
        // Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToMedications = { navController.navigate(Screen.Medications.route) },
                onNavigateToAppointments = { navController.navigate(Screen.Appointments.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Medications List
        composable(Screen.Medications.route) {
            MedicationsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAdd = { navController.navigate(Screen.AddMedication.route) },
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.MedicationDetail.createRoute(id))
                },
                onNavigateToApiSearch = { navController.navigate(Screen.ApiSearch.route) }
            )
        }

        // Add Medication
        composable(Screen.AddMedication.route) {
            AddMedicationScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Medication Detail
        composable(
            route = Screen.MedicationDetail.route,
            arguments = listOf(navArgument("medicationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicationId = backStackEntry.arguments?.getInt("medicationId") ?: 0
            MedicationDetailScreen(
                medicationId = medicationId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Appointments List
        composable(Screen.Appointments.route) {
            AppointmentsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAdd = { navController.navigate(Screen.AddAppointment.route) },
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.AppointmentDetail.createRoute(id))
                }
            )
        }

        // Add Appointment
        composable(Screen.AddAppointment.route) {
            AddAppointmentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Appointment Detail
        composable(
            route = Screen.AppointmentDetail.route,
            arguments = listOf(navArgument("appointmentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getInt("appointmentId") ?: 0
            AppointmentDetailScreen(
                appointmentId = appointmentId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Profile
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // API Search
        composable(Screen.ApiSearch.route) {
            ApiSearchScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}