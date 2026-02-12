package com.guillermo.healthcare.domain.model

import com.guillermo.healthcare.data.local.entity.Cita
import com.guillermo.healthcare.data.local.entity.Doctor
import com.guillermo.healthcare.data.local.entity.Medicamento
import com.guillermo.healthcare.data.local.entity.Sintoma

// Medicamento
fun Medicamento.aModelo() = ModeloMedicamento(
    id = id,
    nombre = nombre,
    dosis = dosis,
    frecuencia = frecuencia,
    fechaInicio = fechaInicio,
    fechaFin = fechaFin,
    notas = notas
)

fun ModeloMedicamento.aEntidad() = Medicamento(
    id = id,
    nombre = nombre,
    dosis = dosis,
    frecuencia = frecuencia,
    fechaInicio = fechaInicio,
    fechaFin = fechaFin,
    notas = notas
)

// Cita
fun Cita.aModelo() = ModeloCita(
    id = id,
    nombreDoctor = nombreDoctor,
    especialidad = especialidad,
    fecha = fecha,
    hora = hora,
    lugar = lugar,
    notas = notas
)

fun ModeloCita.aEntidad() = Cita(
    id = id,
    nombreDoctor = nombreDoctor,
    especialidad = especialidad,
    fecha = fecha,
    hora = hora,
    lugar = lugar,
    notas = notas
)

// Sintoma
fun Sintoma.aModelo() = ModeloSintoma(
    id = id,
    nombre = nombre,
    intensidad = intensidad,
    fecha = fecha,
    hora = hora,
    descripcion = descripcion,
    desencadenantes = desencadenantes
)

fun ModeloSintoma.aEntidad() = Sintoma(
    id = id,
    nombre = nombre,
    intensidad = intensidad,
    fecha = fecha,
    hora = hora,
    descripcion = descripcion,
    desencadenantes = desencadenantes
)

// Doctor
fun Doctor.aModelo() = ModeloDoctor(
    id = id,
    nombre = nombre,
    especialidad = especialidad,
    telefono = telefono,
    email = email,
    direccion = direccion,
    notas = notas
)

fun ModeloDoctor.aEntidad() = Doctor(
    id = id,
    nombre = nombre,
    especialidad = especialidad,
    telefono = telefono,
    email = email,
    direccion = direccion,
    notas = notas
)