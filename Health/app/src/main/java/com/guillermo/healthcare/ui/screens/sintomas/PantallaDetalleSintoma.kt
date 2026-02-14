package com.guillermo.healthcare.ui.screens.sintomas

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
fun PantallaDetalleSintoma(
    sintomaId: Int,
    navController: NavController,
    viewModel: ViewModelSintoma = hiltViewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    val sintoma by viewModel.sintomaSeleccionado.collectAsState()

    LaunchedEffect(sintomaId) {
        viewModel.cargarSintomaPorId(sintomaId)
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar síntoma") },
            text = { Text("¿Estás seguro de que quieres eliminar este síntoma?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val sintomaActual = sintoma
                        if (sintomaActual != null) {
                            viewModel.eliminarSintoma(sintomaActual)
                            navController.navigateUp()
                        }
                        mostrarDialogo = false
                    }
                ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Síntoma") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Pantalla.FormularioSintoma.crearRuta(sintomaId))
                    }) { Icon(Icons.Default.Edit, "Editar") }
                    IconButton(onClick = { mostrarDialogo = true }) {
                        Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { paddingValues ->
        if (sintoma == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) { Text("Cargando...") }
        } else {
            val sintomaActual = sintoma!!
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(sintomaActual.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Divider()
                        FilaDetalle("🌡Intensidad", "${sintomaActual.intensidad}/10")
                        FilaDetalle("Fecha", sintomaActual.fecha)
                        FilaDetalle("Hora", sintomaActual.hora)
                        sintomaActual.descripcion?.let { FilaDetalle("Descripción", it) }
                        sintomaActual.desencadenantes?.let { FilaDetalle("⚡ Desencadenantes", it) }
                    }
                }
                Button(
                    onClick = { navController.navigate(Pantalla.FormularioSintoma.crearRuta(sintomaId)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Edit, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Editar Síntoma")
                }
                OutlinedButton(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Eliminar Síntoma")
                }
            }
        }
    }
}