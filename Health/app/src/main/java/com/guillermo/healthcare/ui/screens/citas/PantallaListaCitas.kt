package com.guillermo.healthcare.ui.screens.citas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.ui.navigation.Pantalla
import com.guillermo.healthcare.ui.screens.citas.ViewModelCita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaCitas(
    navController: NavController,
    viewModel: ViewModelCita = hiltViewModel()
) {
    val citas by viewModel.citas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Citas Médicas") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Pantalla.FormularioCita.crearRuta()) }
            ) {
                Icon(Icons.Default.Add, "Añadir")
            }
        }
    ) { paddingValues ->
        if (citas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay citas. ¡Añade una!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(citas) { cita ->
                    TarjetaCita(
                        cita = cita,
                        onClickItem = {
                            navController.navigate(Pantalla.DetalleCita.crearRuta(cita.id))
                        },
                        onClickEliminar = { viewModel.eliminarCita(cita) }
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaCita(
    cita: Cita,
    onClickItem: () -> Unit,
    onClickEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClickItem() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cita.nombreDoctor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Especialidad: ${cita.especialidad}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${cita.fecha} a las ${cita.hora}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${cita.lugar}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onClickEliminar) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}