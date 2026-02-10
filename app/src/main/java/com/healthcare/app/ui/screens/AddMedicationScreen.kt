package com.healthcare.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.healthcare.app.data.local.entity.MedicationEntity
import com.healthcare.app.ui.viewmodel.MedicationViewModel

/**
 * Pantalla para añadir un nuevo medicamento
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    onNavigateBack: () -> Unit,
    viewModel: MedicationViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }
    var reminderEnabled by remember { mutableStateOf(false) }

    // Validación
    val isFormValid = name.isNotBlank() && dosage.isNotBlank() && frequency.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Medicamento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isFormValid) {
                        val medication = MedicationEntity(
                            name = name.trim(),
                            dosage = dosage.trim(),
                            frequency = frequency.trim(),
                            startDate = System.currentTimeMillis(),
                            notes = notes.trim().ifBlank { null },
                            isActive = isActive,
                            reminderEnabled = reminderEnabled
                        )
                        viewModel.insertMedication(medication)
                        onNavigateBack()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Save, "Guardar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nombre del medicamento
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del medicamento *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = name.isBlank()
            )

            // Dosis
            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosis *") },
                placeholder = { Text("Ej: 500mg, 2 pastillas") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = dosage.isBlank()
            )

            // Frecuencia
            OutlinedTextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = { Text("Frecuencia *") },
                placeholder = { Text("Ej: Cada 8 horas, 2 veces al día") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = frequency.isBlank()
            )

            // Notas
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas (opcional)") },
                placeholder = { Text("Tomar con comida, antes de dormir...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Divider()

            // Medicamento activo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Medicamento activo",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = isActive,
                    onCheckedChange = { isActive = it }
                )
            }

            // Recordatorio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Activar recordatorio",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = reminderEnabled,
                    onCheckedChange = { reminderEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de validación
            if (!isFormValid) {
                Text(
                    text = "* Campos obligatorios",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}