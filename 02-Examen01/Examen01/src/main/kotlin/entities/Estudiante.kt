package org.example.entities

import java.time.LocalDate

data class Estudiante(
    val codigo: String, // Código único del estudiante
    var nombre: String,
    var edad: Int,
    var IRA: Float,
    var estado: String, // Activo/Inactivo
    var fechaIngreso: LocalDate
)