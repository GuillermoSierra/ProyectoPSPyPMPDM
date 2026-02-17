package com.guillermo.healthcare.ui.screens.medicamentos

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
fun PantallaDetalleMedicamento(
    medicamentoId: Int,
    navController: NavController,
    viewModel: ViewModelMedicamento = hiltViewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(medicamentoId) {
        viewModel.cargarMedicamentoPorId(medicamentoId)
    }

    val medicamento by viewModel.medicamentoSeleccionado.collectAsState()

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar medicamento") },
            text = { Text("¿Estás seguro de que quieres eliminar ${medicamento?.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        medicamento?.let {
                            viewModel.eliminarMedicamento(it)
                            navController.navigateUp()
                        }
                        mostrarDialogo = false
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Medicamento") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    // Botón Editar
                    IconButton(
                        onClick = {
                            navController.navigate(
                                Pantalla.FormularioMedicamento.crearRuta(medicamentoId)
                            )
                        }
                    ) {
                        Icon(Icons.Default.Edit, "Editar")
                    }
                    // Botón Eliminar
                    IconButton(
                        onClick = { mostrarDialogo = true }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (medicamento == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Tarjeta principal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = medicamento!!.nombre,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Divider()

                        FilaDetalle(etiqueta = "Dosis", valor = medicamento!!.dosis)
                        FilaDetalle(etiqueta = "Frecuencia", valor = medicamento!!.frecuencia)
                        FilaDetalle(etiqueta = "Fecha de inicio", valor = medicamento!!.fechaInicio)

                        FilaDetalle(
                            etiqueta = "Fecha de fin",
                            valor = medicamento!!.fechaFin ?: "Indefinida"
                        )

                        medicamento!!.notas?.let {
                            Divider()
                            FilaDetalle(etiqueta = "Notas", valor = it)
                        }
                    }
                }

                Button(
                    onClick = {
                        navController.navigate(
                            Pantalla.FormularioMedicamento.crearRuta(medicamentoId)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Editar Medicamento")
                }

                OutlinedButton(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Eliminar Medicamento")
                }
            }
        }
    }
}