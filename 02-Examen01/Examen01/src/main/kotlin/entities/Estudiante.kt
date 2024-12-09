package org.example

data class Estudiante(
    val codigo: String, // Código único del estudiante
    var nombre: String,
    var edad: Int,
    var IRA: Float,
    var estado: String, // Activo/Inactivo
    var codigoMateria: String = "" // Asociar estudiante con la materia

) {
    // Metodo para representar el estudiante en formato de texto
    fun toFileFormat(): String {
        return "$codigo,$nombre,$edad,$IRA,$estado,$codigoMateria"
    }
}
