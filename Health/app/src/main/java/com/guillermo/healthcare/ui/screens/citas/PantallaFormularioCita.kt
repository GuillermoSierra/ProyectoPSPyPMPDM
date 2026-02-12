package com.guillermo.healthcare.ui.screens.citas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.ui.screens.citas.ViewModelCita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioCita(
    citaId: Int?,
    navController: NavController,
    viewModel: ViewModelCita = hiltViewModel()
) {
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
                value = nombreDoctor, onValueChange = { nombreDoctor = it; errorNombreDoctor = false },
                label = { Text("Nombre del doctor *") }, modifier = Modifier.fillMaxWidth(),
                isError = errorNombreDoctor,
                supportingText = if (errorNombreDoctor) {{ Text("El nombre del doctor es obligatorio") }} else null
            )
            OutlinedTextField(
                value = especialidad, onValueChange = { especialidad = it; errorEspecialidad = false },
                label = { Text("Especialidad *") }, modifier = Modifier.fillMaxWidth(),
                isError = errorEspecialidad,
                supportingText = if (errorEspecialidad) {{ Text("La especialidad es obligatoria") }} else null
            )
            OutlinedTextField(
                value = fecha, onValueChange = { fecha = it; errorFecha = false },
                label = { Text("Fecha (ej: 2026-02-15) *") }, modifier = Modifier.fillMaxWidth(),
                isError = errorFecha,
                supportingText = if (errorFecha) {{ Text("La fecha es obligatoria") }} else null
            )
            OutlinedTextField(
                value = hora, onValueChange = { hora = it; errorHora = false },
                label = { Text("Hora (ej: 10:30) *") }, modifier = Modifier.fillMaxWidth(),
                isError = errorHora,
                supportingText = if (errorHora) {{ Text("La hora es obligatoria") }} else null
            )
            OutlinedTextField(
                value = lugar, onValueChange = { lugar = it; errorLugar = false },
                label = { Text("Lugar *") }, modifier = Modifier.fillMaxWidth(),
                isError = errorLugar,
                supportingText = if (errorLugar) {{ Text("El lugar es obligatorio") }} else null
            )
            OutlinedTextField(
                value = notas, onValueChange = { notas = it },
                label = { Text("Notas (opcional)") }, modifier = Modifier.fillMaxWidth(),
                minLines = 3, maxLines = 5
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    errorNombreDoctor = nombreDoctor.isBlank()
                    errorEspecialidad = especialidad.isBlank()
                    errorFecha = fecha.isBlank()
                    errorHora = hora.isBlank()
                    errorLugar = lugar.isBlank()

                    if (!errorNombreDoctor && !errorEspecialidad && !errorFecha && !errorHora && !errorLugar) {
                        val cita = Cita(
                            id = citaId ?: 0,
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