package com.guillermo.healthcare.ui.screens.doctores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guillermo.healthcare.data.local.entity.Doctor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioDoctor(
    doctorId: Int?,
    navController: NavController,
    viewModel: ViewModelDoctor = hiltViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf(false) }
    var errorEspecialidad by remember { mutableStateOf(false) }
    var errorTelefono by remember { mutableStateOf(false) }
    var errorEmail by remember { mutableStateOf(false) }
    var errorDireccion by remember { mutableStateOf(false) }

    LaunchedEffect(doctorId) { if (doctorId != null) viewModel.cargarDoctorPorId(doctorId) }

    val doctorSeleccionado by viewModel.doctorSeleccionado.collectAsState()

    LaunchedEffect(doctorSeleccionado) {
        doctorSeleccionado?.let {
            nombre = it.nombre
            especialidad = it.especialidad
            telefono = it.telefono
            email = it.email ?: ""
            direccion = it.direccion
            notas = it.notas ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (doctorId == null) "Nuevo Doctor" else "Editar Doctor") },
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
                label = { Text("Nombre del doctor *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorNombre,
                supportingText = if (errorNombre) {{ Text("El nombre es obligatorio") }} else null
            )

            OutlinedTextField(
                value = especialidad,
                onValueChange = { especialidad = it; errorEspecialidad = false },
                label = { Text("Especialidad *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorEspecialidad,
                supportingText = if (errorEspecialidad) {{ Text("La especialidad es obligatoria") }} else null
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    if (it.length <= 9) telefono = it
                    errorTelefono = false
                },
                label = { Text("Teléfono * (9 dígitos)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = errorTelefono,
                supportingText = if (errorTelefono) {{
                    Text("El teléfono debe tener exactamente 9 dígitos")
                }} else null
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; errorEmail = false },
                label = { Text("Email (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = errorEmail,
                supportingText = if (errorEmail) {{
                    Text("El formato del email no es válido")
                }} else null
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it; errorDireccion = false },
                label = { Text("Dirección *") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorDireccion,
                supportingText = if (errorDireccion) {{ Text("La dirección es obligatoria") }} else null
            )

            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Notas (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Validaciones
                    errorNombre = nombre.isBlank()
                    errorEspecialidad = especialidad.isBlank()
                    errorDireccion = direccion.isBlank()

                    // Validación teléfono - exactamente 9 dígitos
                    val telefonoValido = telefono.matches(Regex("^[0-9]{9}$"))
                    errorTelefono = telefono.isBlank() || !telefonoValido

                    // Validación email - formato correcto si no está vacío
                    val emailValido = email.isBlank() || email.matches(
                        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                    )
                    errorEmail = !emailValido

                    if (!errorNombre && !errorEspecialidad && !errorTelefono && !errorEmail && !errorDireccion) {
                        val doctor = Doctor(
                            id = doctorId ?: 0,
                            nombre = nombre,
                            especialidad = especialidad,
                            telefono = telefono,
                            email = email.ifBlank { null },
                            direccion = direccion,
                            notas = notas.ifBlank { null }
                        )
                        if (doctorId == null) viewModel.insertarDoctor(doctor)
                        else viewModel.actualizarDoctor(doctor)
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (doctorId == null) "Crear Doctor" else "Actualizar Doctor")
            }
        }
    }
}