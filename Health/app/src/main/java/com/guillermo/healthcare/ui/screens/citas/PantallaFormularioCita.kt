package com.guillermo.healthcare.ui.screens.citas

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
import com.guillermo.healthcare.data.local.entity.Cita
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioCita(
    citaId: Int?,
    userId: String,
    navController: NavController,
    viewModel: ViewModelCita = hiltViewModel()
) {
    val contexto = LocalContext.current
    val calendar = Calendar.getInstance()

    var nombreDoctor by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    var errorNombreDoctor by remember { mutableStateOf(false) }
    var errorEspecialidad by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var errorHora by remember { mutableStateOf(false) }
    var errorLugar by remember { mutableStateOf(false) }

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

    LaunchedEffect(citaId) {
        if (citaId != null) viewModel.cargarCitaPorId(citaId)
    }

    val citaSeleccionada by viewModel.citaSeleccionada.collectAsState()

    LaunchedEffect(citaSeleccionada) {
        citaSeleccionada?.let {
            nombreDoctor = it.nombreDoctor
            especialidad = it.especialidad
            fecha = it.fecha
            hora = it.hora
            lugar = it.lugar
            notas = it.notas ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (citaId == null) "Nueva Cita" else "Editar Cita") },
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
                value = nombreDoctor,
                onValueChange = { nombreDoctor = it; errorNombreDoctor = false },
                label = { Text("Nombre del doctor *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorNombreDoctor,
                supportingText = if (errorNombreDoctor) {{ Text("El nombre del doctor es obligatorio") }} else null
            )
            OutlinedTextField(
                value = especialidad,
                onValueChange = { especialidad = it; errorEspecialidad = false },
                label = { Text("Especialidad *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorEspecialidad,
                supportingText = if (errorEspecialidad) {{ Text("La especialidad es obligatoria") }} else null
            )

            // Fecha con DatePicker
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

            // Hora con TimePicker
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
                value = lugar,
                onValueChange = { lugar = it; errorLugar = false },
                label = { Text("Lugar *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorLugar,
                supportingText = if (errorLugar) {{ Text("El lugar es obligatorio") }} else null
            )
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
                    errorNombreDoctor = nombreDoctor.isBlank()
                    errorEspecialidad = especialidad.isBlank()
                    errorFecha = fecha.isBlank()
                    errorHora = hora.isBlank()
                    errorLugar = lugar.isBlank()

                    if (!errorNombreDoctor && !errorEspecialidad && !errorFecha && !errorHora && !errorLugar) {

                        android.util.Log.d("CITA", "userId al crear: $userId")

                        val cita = Cita(
                            id = citaId ?: 0,
                            userId = userId,
                            nombreDoctor = nombreDoctor,
                            especialidad = especialidad,
                            fecha = fecha,
                            hora = hora,
                            lugar = lugar,
                            notas = notas.ifBlank { null }
                        )
                        if (citaId == null) viewModel.insertarCita(cita)
                        else viewModel.actualizarCita(cita)
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (citaId == null) "Crear Cita" else "Actualizar Cita")
            }
        }
    }
}