package com.guillermo.healthcare.ui.screens.sintomas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.local.entity.Sintoma
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioSintoma(
    sintomaId: Int?,
    userId: String,
    navController: NavController,
    viewModel: ViewModelSintoma = hiltViewModel()
) {
    val contexto = LocalContext.current
    val calendar = Calendar.getInstance()

    var nombre by remember { mutableStateOf("") }
    var intensidad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var desencadenantes by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var errorHora by remember { mutableStateOf(false) }
    var errorIntensidad by remember { mutableStateOf(false) }

    val datePickerDialog = DatePickerDialog(
        contexto,
        { _, year, month, day ->
            fecha = String.format("%04d-%02d-%02d", year, month + 1, day)
            errorFecha = false
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = calendar.timeInMillis
    }

    val timePickerDialog = TimePickerDialog(
        contexto,
        { _, hour, minute ->
            val ahora = Calendar.getInstance()
            val seleccionada = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            if (fecha == String.format("%04d-%02d-%02d",
                    ahora.get(Calendar.YEAR),
                    ahora.get(Calendar.MONTH) + 1,
                    ahora.get(Calendar.DAY_OF_MONTH))
                && seleccionada.before(ahora)
            ) {
                hora = String.format("%02d:%02d",
                    ahora.get(Calendar.HOUR_OF_DAY),
                    ahora.get(Calendar.MINUTE))
            } else {
                hora = String.format("%02d:%02d", hour, minute)
            }
            errorHora = false
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val sintomaSeleccionado by viewModel.sintomaSeleccionado.collectAsState()

    LaunchedEffect(sintomaId) {
        if (sintomaId != null) viewModel.cargarSintomaPorId(sintomaId)
    }

    LaunchedEffect(sintomaSeleccionado) {
        sintomaSeleccionado?.let {
            nombre = it.nombre
            intensidad = it.intensidad.toString()
            fecha = it.fecha
            hora = it.hora
            descripcion = it.descripcion ?: ""
            desencadenantes = it.desencadenantes ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (sintomaId == null) "Nuevo Síntoma" else "Editar Síntoma") },
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
                label = { Text("Nombre del síntoma *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorNombre,
                supportingText = if (errorNombre) {{ Text("El nombre es obligatorio") }} else null
            )

            OutlinedTextField(
                value = intensidad,
                onValueChange = { intensidad = it; errorIntensidad = false },
                label = { Text("Intensidad (1-10) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorIntensidad,
                supportingText = if (errorIntensidad) {{ Text("Introduce un valor entre 1 y 10") }} else null
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = {},
                label = { Text("Fecha *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                enabled = false,
                isError = errorFecha,
                supportingText = if (errorFecha) {{ Text("La fecha es obligatoria") }} else null,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.CalendarMonth, "Seleccionar fecha")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = if (errorFecha) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            OutlinedTextField(
                value = hora,
                onValueChange = {},
                label = { Text("Hora *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { timePickerDialog.show() },
                enabled = false,
                isError = errorHora,
                supportingText = if (errorHora) {{ Text("La hora es obligatoria") }} else null,
                trailingIcon = {
                    IconButton(onClick = { timePickerDialog.show() }) {
                        Icon(Icons.Default.Schedule, "Seleccionar hora")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = if (errorHora) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            OutlinedTextField(
                value = desencadenantes,
                onValueChange = { desencadenantes = it },
                label = { Text("Desencadenantes (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    errorNombre = nombre.isBlank()
                    errorFecha = fecha.isBlank()
                    errorHora = hora.isBlank()
                    val intensidadInt = intensidad.toIntOrNull()
                    errorIntensidad = intensidadInt == null || intensidadInt !in 1..10

                    if (!errorNombre && !errorFecha && !errorHora && !errorIntensidad) {

                        android.util.Log.d("SINTOMA", "userId al crear: $userId")

                        val sintoma = Sintoma(
                            id = sintomaId ?: 0,
                            userId = userId,
                            nombre = nombre,
                            intensidad = intensidadInt!!,
                            fecha = fecha,
                            hora = hora,
                            descripcion = descripcion.ifBlank { null },
                            desencadenantes = desencadenantes.ifBlank { null }
                        )
                        if (sintomaId == null) viewModel.insertarSintoma(sintoma)
                        else viewModel.actualizarSintoma(sintoma)
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (sintomaId == null) "Crear Síntoma" else "Actualizar Síntoma")
            }
        }
    }
}