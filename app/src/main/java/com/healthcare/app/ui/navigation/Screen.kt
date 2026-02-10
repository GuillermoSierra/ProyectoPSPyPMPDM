package com.healthcare.app.ui.navigation

/**
 * Rutas de navegación de la aplicación
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Medications : Screen("medications")
    object MedicationDetail : Screen("medication/{medicationId}") {
        fun createRoute(medicationId: Int) = "medication/$medicationId"
    }
    object AddMedication : Screen("add_medication")
    object Appointments : Screen("appointments")
    object AppointmentDetail : Screen("appointment/{appointmentId}") {
        fun createRoute(appointmentId: Int) = "appointment/$appointmentId"
    }
    object AddAppointment : Screen("add_appointment")
    object Profile : Screen("profile")
    object ApiSearch : Screen("api_search")
}