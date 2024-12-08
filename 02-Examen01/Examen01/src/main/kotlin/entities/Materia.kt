package org.example.entities


class Materia(
    val codigo: String, // Código único de la materia
    var nombre: String,
    var creditos: Int,
    var semestre: Int,
    var profesor: String
) {
    val estudiantes = mutableListOf<Estudiante>() // Relación UNO a MUCHOS

    fun agregarEstudiante(estudiante: Estudiante) {
        estudiantes.add(estudiante)
        println("Estudiante ${estudiante.nombre} agregado a la materia $nombre.")
    }

    fun eliminarEstudiante(codigoEstudiante: String) {
        val estudiante = estudiantes.find { it.codigo == codigoEstudiante }
        if (estudiante != null) {
            estudiantes.remove(estudiante)
            println("Estudiante ${estudiante.nombre} eliminado de la materia $nombre.")
        } else {
            println("No se encontró el estudiante con el código $codigoEstudiante.")
        }
    }

    fun listarEstudiantes() {
        if (estudiantes.isEmpty()) {
            println("No hay estudiantes registrados en $nombre.")
        } else {
            println("Estudiantes en la materia $nombre:")
            estudiantes.forEach { println(it) }
        }
    }
}
