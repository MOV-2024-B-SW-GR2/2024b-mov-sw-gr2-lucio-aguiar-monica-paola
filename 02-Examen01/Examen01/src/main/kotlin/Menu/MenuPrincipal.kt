package org.example.Menu

import org.example.entities.Materia
import org.example.entities.Estudiante
import java.time.LocalDate

fun menuPrincipal() {
    val materias = mutableListOf<Materia>() // Lista de materias
    while (true) {
        println("\nMenú Principal")
        println("1. Ingresar nueva materia")
        println("2. Modificar una materia")
        println("3. Eliminar una materia")
        println("4. Agregar un estudiante a una materia")
        println("5. Modificar un estudiante")
        println("6. Eliminar un estudiante de una materia")
        println("7. Listar registros")
        println("8. Salir")
        print("Selecciona una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                // Crear nueva materia
                print("Código de la materia: ")
                val codigo = readLine().orEmpty()
                print("Nombre: ")
                val nombre = readLine().orEmpty()
                print("Créditos: ")
                val creditos = readLine()?.toIntOrNull() ?: 0
                print("Semestre: ")
                val semestre = readLine()?.toIntOrNull() ?: 0
                print("Profesor: ")
                val profesor = readLine().orEmpty()

                materias.add(Materia(codigo, nombre, creditos, semestre, profesor))
                println("Materia $nombre registrada correctamente.")
            }
            2 -> {
                // Modificar una materia
                print("Código de la materia a modificar: ")
                val codigo = readLine().orEmpty()
                val materia = materias.find { it.codigo == codigo }
                if (materia != null) {
                    print("Nuevo nombre (${materia.nombre}): ")
                    materia.nombre = readLine().orEmpty()
                    print("Nuevos créditos (${materia.creditos}): ")
                    materia.creditos = readLine()?.toIntOrNull() ?: materia.creditos
                    print("Nuevo semestre (${materia.semestre}): ")
                    materia.semestre = readLine()?.toIntOrNull() ?: materia.semestre
                    print("Nuevo profesor (${materia.profesor}): ")
                    materia.profesor = readLine().orEmpty()
                    println("Materia actualizada correctamente.")
                } else {
                    println("Materia no encontrada.")
                }
            }
            3 -> {
                // Eliminar una materia
                print("Código de la materia a eliminar: ")
                val codigo = readLine().orEmpty()
                if (materias.removeIf { it.codigo == codigo }) {
                    println("Materia eliminada correctamente.")
                } else {
                    println("No se encontró la materia.")
                }
            }
            4 -> {
                // Agregar estudiante a una materia
                print("Código de la materia: ")
                val codigoMateria = readLine().orEmpty()
                val materia = materias.find { it.codigo == codigoMateria }
                if (materia != null) {
                    print("Código del estudiante: ")
                    val codigo = readLine().orEmpty()
                    print("Nombre: ")
                    val nombre = readLine().orEmpty()
                    print("Edad: ")
                    val edad = readLine()?.toIntOrNull() ?: 0
                    print("IRA: ")
                    val IRA = readLine()?.toFloatOrNull() ?: 0.0f
                    print("Estado (Activo/Inactivo): ")
                    val estado = readLine().orEmpty()
                    print("Fecha de ingreso (YYYY-MM-DD): ")
                    val fecha = LocalDate.parse(readLine().orEmpty())

                    val estudiante = Estudiante(codigo, nombre, edad, IRA, estado, fecha)
                    materia.agregarEstudiante(estudiante)
                } else {
                    println("No se encontró la materia.")
                }
            }
            5 -> {
                // Modificar un estudiante
                print("Código de la materia: ")
                val codigoMateria = readLine().orEmpty()
                val materia = materias.find { it.codigo == codigoMateria }
                if (materia != null) {
                    print("Código del estudiante a modificar: ")
                    val codigoEstudiante = readLine().orEmpty()
                    val estudiante = materia.estudiantes.find { it.codigo == codigoEstudiante }
                    if (estudiante != null) {
                        print("Nuevo nombre (${estudiante.nombre}): ")
                        estudiante.nombre = readLine().orEmpty()
                        print("Nueva edad (${estudiante.edad}): ")
                        estudiante.edad = readLine()?.toIntOrNull() ?: estudiante.edad
                        print("Nuevo IRA (${estudiante.IRA}): ")
                        estudiante.IRA = readLine()?.toFloatOrNull() ?: estudiante.IRA
                        print("Nuevo estado (${estudiante.estado}): ")
                        estudiante.estado = readLine().orEmpty()
                        println("Estudiante actualizado correctamente.")
                    } else {
                        println("Estudiante no encontrado.")
                    }
                } else {
                    println("Materia no encontrada.")
                }
            }
            6 -> {
                // Eliminar estudiante de una materia
                print("Código de la materia: ")
                val codigoMateria = readLine().orEmpty()
                val materia = materias.find { it.codigo == codigoMateria }
                if (materia != null) {
                    print("Código del estudiante a eliminar: ")
                    val codigoEstudiante = readLine().orEmpty()
                    materia.eliminarEstudiante(codigoEstudiante)
                } else {
                    println("Materia no encontrada.")
                }
            }
            7 -> {
                // Listar registros
                println("\n============================================")
                println("             LISTADO DE MATERIAS            ")
                println("============================================")
                materias.forEach { materia ->
                    println("Materia: ${materia.nombre} (${materia.codigo})")
                    if (materia.estudiantes.isEmpty()) {
                        println("  No hay estudiantes registrados en ${materia.nombre}.")
                    } else {
                        println("  Estudiantes registrados:")
                        println("  ----------------------------------------------------------------------------")
                        println("  | Código    | Nombre           | Edad | IRA  | Estado  | Fecha Ingreso |")
                        println("  ----------------------------------------------------------------------------")
                        materia.estudiantes.forEach { estudiante ->
                            println(
                                String.format(
                                    "  | %-9s | %-15s | %-4d | %-4.2f | %-7s | %-13s |",
                                    estudiante.codigo,
                                    estudiante.nombre,
                                    estudiante.edad,
                                    estudiante.IRA,
                                    estudiante.estado,
                                    estudiante.fechaIngreso
                                )
                            )
                        }
                        println("  -----------------------------------------------------")
                    }
                    println() // Espacio entre materias
                }
            }
            8 -> {
                println("¡Hasta luego!")
                break
            }
            else -> println("Opción no válida.")
        }
    }
}