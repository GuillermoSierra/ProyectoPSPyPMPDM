package com.guillermo.healthcare.ui.screens.doctores

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
import com.guillermo.healthcare.ui.components.FilaDetalle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleDoctor(
    doctorId: Int,
    navController: NavController,
    viewModel: ViewModelDoctor = hiltViewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(doctorId) { viewModel.cargarDoctorPorId(doctorId) }

    val doctor by viewModel.doctorSeleccionado.collectAsState()

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar doctor") },
            text = { Text("¿Estás seguro de que quieres eliminar al Dr. ${doctor?.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    doctor?.let { viewModel.eliminarDoctor(it); navController.navigateUp() }
                    mostrarDialogo = false
                }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") } }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Doctor") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Pantalla.FormularioDoctor.crearRuta(doctorId)) }) { Icon(Icons.Default.Edit, "Editar") }
                    IconButton(onClick = { mostrarDialogo = true }) { Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error) }
                }
            )
        }
    ) { paddingValues ->
        if (doctor == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) { Text("Cargando...") }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Dr. ${doctor!!.nombre}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Divider()
                        FilaDetalle("Especialidad", doctor!!.especialidad)
                        FilaDetalle("Teléfono", doctor!!.telefono)
                        doctor!!.email?.let { FilaDetalle("✉Email", it) }
                        FilaDetalle("Dirección", doctor!!.direccion)
                        doctor!!.notas?.let { Divider(); FilaDetalle("Notas", it) }
                    }
                }
                Button(onClick = { navController.navigate(Pantalla.FormularioDoctor.crearRuta(doctorId)) }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Edit, null); Spacer(Modifier.width(8.dp)); Text("Editar Doctor")
                }
                OutlinedButton(onClick = { mostrarDialogo = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                    Icon(Icons.Default.Delete, null); Spacer(Modifier.width(8.dp)); Text("Eliminar Doctor")
                }
            }
        }
    }
}