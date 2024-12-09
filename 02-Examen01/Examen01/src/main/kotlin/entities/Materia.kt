package org.example

data class Materia(
    val codigoMateria: String,
    val nombre: String,
    val semestre: Int,
    val creditos: Int,
    val profesor: String
) {
    // Metodo para representar la materia en formato de texto
    fun toFileFormat(): String {
        return "$codigoMateria,$nombre,$semestre,$creditos,$profesor"
    }
}
