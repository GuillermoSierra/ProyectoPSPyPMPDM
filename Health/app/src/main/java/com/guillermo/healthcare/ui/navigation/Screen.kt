package com.guillermo.healthcare.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MedicationList : Screen("medication_list")
    object MedicationDetail : Screen("medication_detail/{medicationId}") {
        fun createRoute(medicationId: Int) = "medication_detail/$medicationId"
    }
    object MedicationForm : Screen("medication_form?medicationId={medicationId}") {
        fun createRoute(medicationId: Int? = null) =
            if (medicationId != null) "medication_form?medicationId=$medicationId"
            else "medication_form"
    }
}