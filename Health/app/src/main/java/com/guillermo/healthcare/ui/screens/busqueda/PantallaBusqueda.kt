package com.guillermo.healthcare.ui.screens.busqueda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.remote.dto.MedicamentoDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBusqueda(
    navController: NavController,
    viewModel: ViewModelBusqueda = hiltViewModel()
) {
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Medicamentos") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = estado.consulta,
                onValueChange = { viewModel.actualizarConsulta(it) },
                label = { Text("Nombre del medicamento") },
                placeholder = { Text("Ej: Ibuprofen, Aspirin...") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.buscarMedicamentos() }) {
                        Icon(Icons.Default.Search, "Buscar")
                    }
                },
                singleLine = true
            )

            // Botón buscar
            Button(
                onClick = { viewModel.buscarMedicamentos() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.cargando && estado.consulta.isNotBlank()
            ) {
                Icon(Icons.Default.Search, null)
                Spacer(Modifier.width(8.dp))
                Text("Buscar en OpenFDA")
            }

            // Estados
            when {
                estado.cargando -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Buscando medicamentos...")
                        }
                    }
                }

                estado.error != null -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${estado.error}",
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }

                estado.resultados.isEmpty() && estado.consulta.isNotBlank() && !estado.cargando -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No se encontraron resultados para '${estado.consulta}'")
                    }
                }

                estado.resultados.isNotEmpty() -> {
                    Text(
                        text = "${estado.resultados.size} resultados encontrados",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(estado.resultados) { medicamento ->
                            TarjetaResultadoAPI(medicamento = medicamento)
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Busca medicamentos en la base de datos de la FDA",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaResultadoAPI(medicamento: MedicamentoDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = medicamento.nombreMarca ?: "Sin nombre",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            medicamento.nombreGenerico?.let {
                Text(
                    text = "Genérico: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            medicamento.fabricante?.let {
                Text(
                    text = "Fabricante: $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            medicamento.via?.let {
                val viaTexto = when (it) {
                    is List<*> -> it.firstOrNull()?.toString() ?: ""
                    else -> it.toString()
                }
                if (viaTexto.isNotBlank()) {
                    Text(
                        text = "Vía: $viaTexto",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            medicamento.formaDosis?.let {
                val formaTexto = when (it) {
                    is List<*> -> it.firstOrNull()?.toString() ?: ""
                    else -> it.toString()
                }
                if (formaTexto.isNotBlank()) {
                    Text(
                        text = "Forma: $formaTexto",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
