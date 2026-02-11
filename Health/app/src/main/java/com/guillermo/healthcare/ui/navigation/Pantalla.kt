package com.guillermo.healthcare.ui.navigation

sealed class Pantalla(val ruta: String) {
    object Inicio : Pantalla("inicio")
    object ListaMedicamentos : Pantalla("lista_medicamentos")
    object DetalleMedicamento : Pantalla("detalle_medicamento/{medicamentoId}") {
        fun crearRuta(medicamentoId: Int) = "detalle_medicamento/$medicamentoId"
    }
    object FormularioMedicamento : Pantalla("formulario_medicamento?medicamentoId={medicamentoId}") {
        fun crearRuta(medicamentoId: Int? = null) =
            if (medicamentoId != null) "formulario_medicamento?medicamentoId=$medicamentoId"
            else "formulario_medicamento"
    }
}