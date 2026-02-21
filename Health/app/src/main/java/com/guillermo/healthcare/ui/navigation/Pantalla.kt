package com.guillermo.healthcare.ui.navigation

sealed class Pantalla(val ruta: String) {

    object Login : Pantalla("login")
    object Inicio : Pantalla("inicio")

    object ListaMedicamentos : Pantalla("lista_medicamentos")
    object DetalleMedicamento : Pantalla("detalle_medicamento/{medicamentoId}") {
        fun crearRuta(medicamentoId: Int) = "detalle_medicamento/$medicamentoId"
    }
    object FormularioMedicamento : Pantalla("formulario_medicamento?medicamentoId={medicamentoId}&nombre={nombre}") {
        fun crearRuta(medicamentoId: Int? = null, nombre: String? = null): String {
            var ruta = if (medicamentoId != null) "formulario_medicamento?medicamentoId=$medicamentoId"
            else "formulario_medicamento"

            if (nombre != null) {
                ruta += if (medicamentoId != null) "&nombre=$nombre" else "?nombre=$nombre"
            }
            return ruta
        }
    }

    object ListaCitas : Pantalla("lista_citas")
    object DetalleCita : Pantalla("detalle_cita/{citaId}") {
        fun crearRuta(citaId: Int) = "detalle_cita/$citaId"
    }
    object FormularioCita : Pantalla("formulario_cita?citaId={citaId}") {
        fun crearRuta(citaId: Int? = null) =
            if (citaId != null) "formulario_cita?citaId=$citaId"
            else "formulario_cita"
    }

    object ListaSintomas : Pantalla("lista_sintomas")
    object DetalleSintoma : Pantalla("detalle_sintoma/{sintomaId}") {
        fun crearRuta(sintomaId: Int) = "detalle_sintoma/$sintomaId"
    }
    object FormularioSintoma : Pantalla("formulario_sintoma?sintomaId={sintomaId}") {
        fun crearRuta(sintomaId: Int? = null) =
            if (sintomaId != null) "formulario_sintoma?sintomaId=$sintomaId"
            else "formulario_sintoma"
    }

    object ListaDoctores : Pantalla("lista_doctores")
    object DetalleDoctor : Pantalla("detalle_doctor/{doctorId}") {
        fun crearRuta(doctorId: Int) = "detalle_doctor/$doctorId"
    }
    object FormularioDoctor : Pantalla("formulario_doctor?doctorId={doctorId}") {
        fun crearRuta(doctorId: Int? = null) =
            if (doctorId != null) "formulario_doctor?doctorId=$doctorId"
            else "formulario_doctor"
    }

    object Busqueda : Pantalla("busqueda")

    object Perfil : Pantalla("perfil")
}