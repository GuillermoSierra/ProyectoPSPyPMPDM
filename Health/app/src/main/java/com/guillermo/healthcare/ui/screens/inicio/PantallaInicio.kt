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
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.ui.navigation.Pantalla
import com.guillermo.healthcare.ui.screens.citas.ViewModelCita
import com.guillermo.healthcare.ui.screens.doctores.ViewModelDoctor
import com.guillermo.healthcare.ui.screens.login.ViewModelAuth
import com.guillermo.healthcare.ui.screens.medicamentos.ViewModelMedicamento
import com.guillermo.healthcare.ui.screens.sintomas.ViewModelSintoma

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    navController: NavController,
    viewModelAuth: ViewModelAuth,
    viewModelMedicamento: ViewModelMedicamento = hiltViewModel(),
    viewModelCita: ViewModelCita = hiltViewModel(),
    viewModelSintoma: ViewModelSintoma = hiltViewModel(),
    viewModelDoctor: ViewModelDoctor = hiltViewModel()
) {
    val contexto = LocalContext.current

    var mostrarDialogoLogout by remember { mutableStateOf(false) }
    var cerrando by remember { mutableStateOf(false) }
    var mostrarMenu by remember { mutableStateOf(false) }  // ← AÑADIDO

    if (mostrarDialogoLogout) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoLogout = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(
                    enabled = !cerrando,
                    onClick = {
                        cerrando = true
                        mostrarDialogoLogout = false
                        viewModelAuth.cerrarSesionLocal(contexto)
                        navController.navigate(Pantalla.Login.ruta) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                ) { Text("Cerrar sesión", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoLogout = false }) { Text("Cancelar") }
            }
        )
    }

    val medicamentos by viewModelMedicamento.medicamentos.collectAsState()
    val citas by viewModelCita.citas.collectAsState()
    val sintomas by viewModelSintoma.sintomas.collectAsState()
    val doctores by viewModelDoctor.doctores.collectAsState()

    val estadoAuth by viewModelAuth.estado.collectAsState()

    LaunchedEffect(estadoAuth.userId) {
        if (estadoAuth.userId.isNotBlank()) {
            viewModelMedicamento.cargarMedicamentos(estadoAuth.userId)
            viewModelCita.cargarCitas(estadoAuth.userId)
            viewModelSintoma.cargarSintomas(estadoAuth.userId)
            viewModelDoctor.cargarDoctores(estadoAuth.userId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HealthCare") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Box {  // ← CAMBIADO: Box con DropdownMenu
                        IconButton(
                            onClick = { if (!cerrando) mostrarMenu = true }
                        ) {
                            Icon(
                                Icons.Default.AccountCircle,  // ← icono persona
                                contentDescription = "Perfil",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        DropdownMenu(
                            expanded = mostrarMenu,
                            onDismissRequest = { mostrarMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Ver perfil") },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null)
                                },
                                onClick = {
                                    mostrarMenu = false
                                    navController.navigate(Pantalla.Perfil.ruta)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                leadingIcon = {
                                    Icon(Icons.Default.Logout, contentDescription = null)
                                },
                                onClick = {
                                    mostrarMenu = false
                                    mostrarDialogoLogout = true
                                }
                            )
                        }
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

            Text(
                text = "Accesos rápidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

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
            BotonNavegacion(
                icono = Icons.Default.Search,
                titulo = "Buscar Medicamentos",
                descripcion = "Busca información de medicamentos en OpenFDA",
                onClick = { navController.navigate(Pantalla.Busqueda.ruta) }
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