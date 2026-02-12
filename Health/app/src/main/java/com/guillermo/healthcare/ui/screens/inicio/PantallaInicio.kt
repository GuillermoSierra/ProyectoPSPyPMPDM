package com.guillermo.healthcare.ui.screens.inicio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.ui.navigation.Pantalla
import com.guillermo.healthcare.ui.screens.citas.ViewModelCita
import com.guillermo.healthcare.ui.screens.doctores.ViewModelDoctor
import com.guillermo.healthcare.ui.screens.medicamentos.ViewModelMedicamento
import com.guillermo.healthcare.ui.screens.sintomas.ViewModelSintoma

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    navController: NavController,
    viewModelMedicamento: ViewModelMedicamento = hiltViewModel(),
    viewModelCita: ViewModelCita = hiltViewModel(),
    viewModelSintoma: ViewModelSintoma = hiltViewModel(),
    viewModelDoctor: ViewModelDoctor = hiltViewModel()
) {
    val medicamentos by viewModelMedicamento.medicamentos.collectAsState()
    val citas by viewModelCita.citas.collectAsState()
    val sintomas by viewModelSintoma.sintomas.collectAsState()
    val doctores by viewModelDoctor.doctores.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HealthCare") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
            // Bienvenida
            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Resumen de tu salud",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tarjetas de resumen
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TarjetaResumen(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Default.Medication,
                    titulo = "Medicamentos",
                    cantidad = medicamentos.size,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                TarjetaResumen(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Default.CalendarMonth,
                    titulo = "Citas",
                    cantidad = citas.size,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TarjetaResumen(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Default.MonitorHeart,
                    titulo = "Síntomas",
                    cantidad = sintomas.size,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
                TarjetaResumen(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Default.Person,
                    titulo = "Doctores",
                    cantidad = doctores.size,
                    color = MaterialTheme.colorScheme.errorContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sección de accesos rápidos
            Text(
                text = "Accesos rápidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Botones de navegación
            BotonNavegacion(
                icono = Icons.Default.Medication,
                titulo = "Medicamentos",
                descripcion = "Gestiona tus medicamentos y dosis",
                onClick = { navController.navigate(Pantalla.ListaMedicamentos.ruta) }
            )

            BotonNavegacion(
                icono = Icons.Default.CalendarMonth,
                titulo = "Citas Médicas",
                descripcion = "Consulta y agenda tus citas",
                onClick = { navController.navigate(Pantalla.ListaCitas.ruta) }
            )

            BotonNavegacion(
                icono = Icons.Default.MonitorHeart,
                titulo = "Síntomas",
                descripcion = "Registra y controla tus síntomas",
                onClick = { navController.navigate(Pantalla.ListaSintomas.ruta) }
            )

            BotonNavegacion(
                icono = Icons.Default.Person,
                titulo = "Mis Doctores",
                descripcion = "Gestiona tus contactos médicos",
                onClick = { navController.navigate(Pantalla.ListaDoctores.ruta) }
            )
        }
    }
}

@Composable
fun TarjetaResumen(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    titulo: String,
    cantidad: Int,
    color: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icono, contentDescription = titulo, modifier = Modifier.size(28.dp))
            Text(
                text = cantidad.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotonNavegacion(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                icono,
                contentDescription = titulo,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}