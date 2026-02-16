package com.guillermo.healthcare.ui.screens.sintomas

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
import com.guillermo.healthcare.data.local.entity.Sintoma

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioSintoma(
    sintomaId: Int?,
    navController: NavController,
    viewModel: ViewModelSintoma = hiltViewModel()
) {
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

    val sintomaSeleccionado by viewModel.sintomaSeleccionado.collectAsState()

    LaunchedEffect(sintomaId) {
        if (sintomaId != null) {
            viewModel.cargarSintomaPorId(sintomaId)
        }
    }

    LaunchedEffect(sintomaSeleccionado) {
        val s = sintomaSeleccionado
        if (s != null) {
            nombre = s.nombre
            intensidad = s.intensidad.toString()
            fecha = s.fecha
            hora = s.hora
            descripcion = s.descripcion ?: ""
            desencadenantes = s.desencadenantes ?: ""
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
                onValueChange = { fecha = it; errorFecha = false },
                label = { Text("Fecha (ej: 2026-02-12) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorFecha,
                supportingText = if (errorFecha) {{ Text("La fecha es obligatoria") }} else null
            )
            OutlinedTextField(
                value = hora,
                onValueChange = { hora = it; errorHora = false },
                label = { Text("Hora (ej: 14:30) *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorHora,
                supportingText = if (errorHora) {{ Text("La hora es obligatoria") }} else null
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
                        val sintoma = Sintoma(
                            id = sintomaId ?: 0,
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