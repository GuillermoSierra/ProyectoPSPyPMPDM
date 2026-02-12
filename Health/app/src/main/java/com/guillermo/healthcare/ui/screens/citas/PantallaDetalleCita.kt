package com.guillermo.healthcare.ui.screens.citas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.ui.navigation.Pantalla
import com.guillermo.healthcare.ui.screens.medicamentos.FilaDetalle
import com.guillermo.healthcare.ui.screens.citas.ViewModelCita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleCita(
    citaId: Int,
    navController: NavController,
    viewModel: ViewModelCita = hiltViewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(citaId) {
        viewModel.cargarCitaPorId(citaId)
    }

    val cita by viewModel.citaSeleccionada.collectAsState()

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar cita") },
            text = { Text("¿Estás seguro de que quieres eliminar esta cita con ${cita?.nombreDoctor}?") },
            confirmButton = {
                TextButton(onClick = {
                    cita?.let { viewModel.eliminarCita(it); navController.navigateUp() }
                    mostrarDialogo = false
                }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Cita") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Pantalla.FormularioCita.crearRuta(citaId))
                    }) { Icon(Icons.Default.Edit, "Editar") }
                    IconButton(onClick = { mostrarDialogo = true }) {
                        Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { paddingValues ->
        if (cita == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Cargando...")
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(cita!!.nombreDoctor, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Divider()
                        FilaDetalle("Especialidad", cita!!.especialidad)
                        FilaDetalle("Fecha", cita!!.fecha)
                        FilaDetalle("Hora", cita!!.hora)
                        FilaDetalle("Lugar", cita!!.lugar)
                        cita!!.notas?.let { Divider(); FilaDetalle("📝 Notas", it) }
                    }
                }
                Button(
                    onClick = { navController.navigate(Pantalla.FormularioCita.crearRuta(citaId)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Edit, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Editar Cita")
                }
                OutlinedButton(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Eliminar Cita")
                }
            }
        }
    }
}