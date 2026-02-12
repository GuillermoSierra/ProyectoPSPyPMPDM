package com.guillermo.healthcare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.local.entity.Medicamento
import com.guillermo.healthcare.ui.viewmodel.ViewModelMedicamento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioMedicamento(
    medicamentoId: Int?,
    navController: NavController,
    viewModel: ViewModelMedicamento = hiltViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    var frecuencia by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf(false) }
    var errorDosis by remember { mutableStateOf(false) }
    var errorFrecuencia by remember { mutableStateOf(false) }
    var errorFechaInicio by remember { mutableStateOf(false) }

    // Cargar medicamento si estamos editando
    LaunchedEffect(medicamentoId) {
        if (medicamentoId != null) {
            viewModel.cargarMedicamentoPorId(medicamentoId)
        }
    }

    val medicamentoSeleccionado by viewModel.medicamentoSeleccionado.collectAsState()

    LaunchedEffect(medicamentoSeleccionado) {
        medicamentoSeleccionado?.let { med ->
            nombre = med.nombre
            dosis = med.dosis
            frecuencia = med.frecuencia
            fechaInicio = med.fechaInicio
            fechaFin = med.fechaFin ?: ""
            notas = med.notas ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (medicamentoId == null) "Nuevo Medicamento" else "Editar Medicamento")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errorNombre = false
                },
                label = { Text("Nombre del medicamento *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorNombre,
                supportingText = if (errorNombre) {{ Text("El nombre es obligatorio") }} else null
            )

            // Campo Dosis
            OutlinedTextField(
                value = dosis,
                onValueChange = {
                    dosis = it
                    errorDosis = false
                },
                label = { Text("Dosis (ej: 500mg) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorDosis,
                supportingText = if (errorDosis) {{ Text("La dosis es obligatoria") }} else null
            )

            // Campo Frecuencia
            OutlinedTextField(
                value = frecuencia,
                onValueChange = {
                    frecuencia = it
                    errorFrecuencia = false
                },
                label = { Text("Frecuencia (ej: Cada 8 horas) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorFrecuencia,
                supportingText = if (errorFrecuencia) {{ Text("La frecuencia es obligatoria") }} else null
            )

            // Campo Fecha Inicio
            OutlinedTextField(
                value = fechaInicio,
                onValueChange = {
                    fechaInicio = it
                    errorFechaInicio = false
                },
                label = { Text("Fecha de inicio (ej: 2026-02-12) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorFechaInicio,
                supportingText = if (errorFechaInicio) {{ Text("La fecha de inicio es obligatoria") }} else null
            )

            // Campo Fecha Fin (opcional)
            OutlinedTextField(
                value = fechaFin,
                onValueChange = { fechaFin = it },
                label = { Text("Fecha de fin (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Notas (opcional)
            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Notas (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Guardar
            Button(
                onClick = {
                    // Validación
                    errorNombre = nombre.isBlank()
                    errorDosis = dosis.isBlank()
                    errorFrecuencia = frecuencia.isBlank()
                    errorFechaInicio = fechaInicio.isBlank()

                    if (!errorNombre && !errorDosis && !errorFrecuencia && !errorFechaInicio) {
                        val medicamento = Medicamento(
                            id = medicamentoId ?: 0,
                            nombre = nombre,
                            dosis = dosis,
                            frecuencia = frecuencia,
                            fechaInicio = fechaInicio,
                            fechaFin = fechaFin.ifBlank { null },
                            notas = notas.ifBlank { null }
                        )

                        if (medicamentoId == null) {
                            viewModel.insertarMedicamento(medicamento)
                        } else {
                            viewModel.actualizarMedicamento(medicamento)
                        }

                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (medicamentoId == null) "Crear Medicamento" else "Actualizar Medicamento")
            }
        }
    }
}