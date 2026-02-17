package com.guillermo.healthcare.ui.screens.medicamentos

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.local.entity.Medicamento
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioMedicamento(
    medicamentoId: Int?,
    userId: String,
    navController: NavController,
    viewModel: ViewModelMedicamento = hiltViewModel()
) {
    val contexto = LocalContext.current
    val calendar = Calendar.getInstance()

    var nombre by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    var frecuencia by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var fechaFinIndefinida by remember { mutableStateOf(false) }
    var notas by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf(false) }
    var errorDosis by remember { mutableStateOf(false) }
    var errorFrecuencia by remember { mutableStateOf(false) }
    var errorFechaInicio by remember { mutableStateOf(false) }

    val datePickerInicio = DatePickerDialog(
        contexto,
        { _, year, month, day ->
            fechaInicio = String.format("%04d-%02d-%02d", year, month + 1, day)
            errorFechaInicio = false
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val datePickerFin = DatePickerDialog(
        contexto,
        { _, year, month, day ->
            fechaFin = String.format("%04d-%02d-%02d", year, month + 1, day)
            fechaFinIndefinida = false
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = calendar.timeInMillis
    }

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
            fechaFinIndefinida = med.fechaFin == null
            notas = med.notas ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (medicamentoId == null) "Nuevo Medicamento" else "Editar Medicamento") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; errorNombre = false },
                label = { Text("Nombre del medicamento *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorNombre,
                supportingText = if (errorNombre) {{ Text("El nombre es obligatorio") }} else null
            )

            OutlinedTextField(
                value = dosis,
                onValueChange = { dosis = it; errorDosis = false },
                label = { Text("Dosis (ej: 500mg) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorDosis,
                supportingText = if (errorDosis) {{ Text("La dosis es obligatoria") }} else null
            )

            OutlinedTextField(
                value = frecuencia,
                onValueChange = { frecuencia = it; errorFrecuencia = false },
                label = { Text("Frecuencia (ej: Cada 8 horas) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorFrecuencia,
                supportingText = if (errorFrecuencia) {{ Text("La frecuencia es obligatoria") }} else null
            )

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = {},
                label = { Text("Fecha de inicio *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerInicio.show() },
                enabled = false,
                isError = errorFechaInicio,
                supportingText = if (errorFechaInicio) {{ Text("La fecha de inicio es obligatoria") }} else null,
                trailingIcon = {
                    IconButton(onClick = { datePickerInicio.show() }) {
                        Icon(Icons.Default.CalendarMonth, "Seleccionar fecha")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = if (errorFechaInicio) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = fechaFinIndefinida,
                    onCheckedChange = {
                        fechaFinIndefinida = it
                        if (it) fechaFin = ""
                    }
                )
                Text("Fecha fin indefinida", modifier = Modifier.clickable {
                    fechaFinIndefinida = !fechaFinIndefinida
                    if (fechaFinIndefinida) fechaFin = ""
                })
            }

            if (!fechaFinIndefinida) {
                OutlinedTextField(
                    value = fechaFin,
                    onValueChange = {},
                    label = { Text("Fecha de fin") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerFin.show() },
                    enabled = false,
                    trailingIcon = {
                        Row {
                            if (fechaFin.isNotBlank()) {
                                IconButton(onClick = { fechaFin = "" }) {
                                    Icon(Icons.Default.Clear, "Limpiar fecha")
                                }
                            }
                            IconButton(onClick = { datePickerFin.show() }) {
                                Icon(Icons.Default.CalendarMonth, "Seleccionar fecha")
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Notas (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    errorNombre = nombre.isBlank()
                    errorDosis = dosis.isBlank()
                    errorFrecuencia = frecuencia.isBlank()
                    errorFechaInicio = fechaInicio.isBlank()

                    if (!errorNombre && !errorDosis && !errorFrecuencia && !errorFechaInicio) {
                        val medicamento = Medicamento(
                            id = medicamentoId ?: 0,
                            userId = userId,
                            nombre = nombre,
                            dosis = dosis,
                            frecuencia = frecuencia,
                            fechaInicio = fechaInicio,
                            fechaFin = if (fechaFinIndefinida) null else fechaFin.ifBlank { null },  // ← CAMBIO
                            notas = notas.ifBlank { null }
                        )
                        if (medicamentoId == null) viewModel.insertarMedicamento(medicamento)
                        else viewModel.actualizarMedicamento(medicamento)
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