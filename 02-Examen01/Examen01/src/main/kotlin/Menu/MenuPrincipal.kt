package org.example

import java.io.File

class MenuPrincipal {

    private val fileNameMaterias = "materias.txt"
    private val fileNameEstudiantes = "estudiantes.txt"

    private val materias = mutableListOf<Materia>()
    private val estudiantes = mutableListOf<Estudiante>()

    // Metodo para cargar las materias y estudiantes desde los archivos
    private fun cargarDatos() {
        cargarMaterias()
        cargarEstudiantes()
    }

    // Cargar las materias desde el archivo
    private fun cargarMaterias() {
        try {
            val file = File(fileNameMaterias)
            if (file.exists()) {
                file.forEachLine { line ->
                    val datos = line.split(",")
                    if (datos.size == 5) {
                        val materia = Materia(
                            datos[0],
                            datos[1],
                            datos[2].toInt(),
                            datos[3].toInt(),
                            datos[4]
                        )
                        materias.add(materia)
                    }
                }
            }
        } catch (e: Exception) {
            println("Error al cargar las materias: ${e.message}")
        }
    }

    // Cargar los estudiantes desde el archivo
    private fun cargarEstudiantes() {
        try {
            val file = File(fileNameEstudiantes)
            if (file.exists()) {
                file.forEachLine { line ->
                    val datos = line.split(",")
                    if (datos.size == 6) {
                        val estudiante = Estudiante(
                            datos[0],
                            datos[1],
                            datos[2].toInt(),
                            datos[3].toFloat(),
                            datos[4],
                            datos[5]
                        )
                        estudiantes.add(estudiante)
                    }
                }
            }
        } catch (e: Exception) {
            println("Error al cargar los estudiantes: ${e.message}")
        }
    }

    // Metodo para mostrar el menú y ejecutar las opciones
    fun mostrarMenu() {
        cargarDatos()  // Cargar los datos al inicio

        var opcion: Int

        do {
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

            opcion = readLine()?.toIntOrNull() ?: 0

            when (opcion) {
                1 -> ingresarMateria()
                2 -> modificarMateria()
                3 -> eliminarMateria()
                4 -> agregarEstudiante()
                5 -> modificarEstudiante()
                6 -> eliminarEstudiante()
                7 -> listarRegistros()
                8 -> println("Saliendo...")
                else -> println("Opción inválida. Por favor, selecciona una opción válida.")
            }
        } while (opcion != 8)
    }

    // Métodos para las opciones del menú

    private fun ingresarMateria() {
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

        val materia = Materia(codigo, nombre, creditos, semestre, profesor)
        materias.add(materia)
        guardarEnArchivo(fileNameMaterias, materia.toFileFormat())
        println("Materia '$nombre' ingresada correctamente.")
    }

    private fun modificarMateria() {
        println("Ingrese el código de la materia que desea modificar: ")
        val codigo = readLine().orEmpty()

        val materia = materias.find { it.codigoMateria == codigo }
        if (materia != null) {
            print("Nuevo nombre (actual: ${materia.nombre}): ")
            val nombre = readLine().orEmpty()
            print("Nuevos créditos (actual: ${materia.creditos}): ")
            val creditos = readLine()?.toIntOrNull() ?: materia.creditos
            print("Nuevo semestre (actual: ${materia.semestre}): ")
            val semestre = readLine()?.toIntOrNull() ?: materia.semestre
            print("Nuevo profesor (actual: ${materia.profesor}): ")
            val profesor = readLine().orEmpty()

            val updatedMateria = Materia(codigo, nombre, creditos, semestre, profesor)
            materias[materias.indexOf(materia)] = updatedMateria

            // Guardar de nuevo la lista de materias en el archivo
            actualizarArchivoMaterias()
            println("Materia '$codigo' modificada correctamente.")
        } else {
            println("Materia no encontrada.")
        }
    }

    private fun eliminarMateria() {
        println("Ingrese el código de la materia que desea eliminar: ")
        val codigo = readLine().orEmpty()

        val materia = materias.find { it.codigoMateria == codigo }
        if (materia != null) {
            materias.remove(materia)
            // Guardar de nuevo la lista de materias en el archivo
            actualizarArchivoMaterias()
            println("Materia '$codigo' eliminada correctamente.")
        } else {
            println("Materia no encontrada.")
        }
    }

    private fun agregarEstudiante() {
        print("Código de la materia: ")
        val codigoMateria = readLine().orEmpty()
        val materia = materias.find { it.codigoMateria == codigoMateria }
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

            val estudiante = Estudiante(codigo, nombre, edad, IRA, estado, codigoMateria)
            estudiantes.add(estudiante)
            guardarEnArchivo(fileNameEstudiantes, estudiante.toFileFormat())
            println("Estudiante '$nombre' agregado correctamente a la materia '${materia.nombre}'.")
        } else {
            println("Materia no encontrada.")
        }
    }

    private fun modificarEstudiante() {
        println("Ingrese el código del estudiante que desea modificar: ")
        val codigoEstudiante = readLine().orEmpty()

        val estudiante = estudiantes.find { it.codigo == codigoEstudiante }
        if (estudiante != null) {
            println("Estás modificando al estudiante '${estudiante.nombre}', asociado a la materia con código '${estudiante.codigoMateria}'.")

            print("Nuevo nombre (actual: ${estudiante.nombre}): ")
            val nombre = readLine().orEmpty()
            print("Nueva edad (actual: ${estudiante.edad}): ")
            val edad = readLine()?.toIntOrNull() ?: estudiante.edad
            print("Nuevo IRA (actual: ${estudiante.IRA}): ")
            val IRA = readLine()?.toFloatOrNull() ?: estudiante.IRA
            print("Nuevo estado (actual: ${estudiante.estado}): ")
            val estado = readLine().orEmpty()

            // Actualizamos el estudiante manteniendo el código de materia actual
            val updatedEstudiante = Estudiante(codigoEstudiante, nombre, edad, IRA, estado, estudiante.codigoMateria)
            estudiantes[estudiantes.indexOf(estudiante)] = updatedEstudiante

            // Guardar de nuevo la lista de estudiantes en el archivo
            actualizarArchivoEstudiantes()
            println("Estudiante '${codigoEstudiante}' modificado correctamente.")
        } else {
            println("Estudiante no encontrado.")
        }
    }

    private fun eliminarEstudiante() {
        println("Ingrese el código del estudiante que desea eliminar: ")
        val codigo = readLine().orEmpty()

        val estudiante = estudiantes.find { it.codigo == codigo }
        if (estudiante != null) {
            estudiantes.remove(estudiante)
            // Guardar de nuevo la lista de estudiantes en el archivo
            actualizarArchivoEstudiantes()
            println("Estudiante '$codigo' eliminado correctamente.")
        } else {
            println("Estudiante no encontrado.")
        }
    }

    private fun listarRegistros() {
        println("\nListado de Materias:")
        materias.forEach { materia ->
            println("Materia: ${materia.nombre} (Código: ${materia.codigoMateria})")
            val estudiantesPorMateria = estudiantes.filter { it.codigoMateria == materia.codigoMateria }
            if (estudiantesPorMateria.isNotEmpty()) {
                println("| Código | Nombre       | Edad | IRA  | Estado    |")
                println("|--------|--------------|------|------|-----------|")
                estudiantesPorMateria.forEach { estudiante ->
                    println("| ${estudiante.codigo} | ${estudiante.nombre.padEnd(12)} | ${estudiante.edad} | ${estudiante.IRA} | ${estudiante.estado} |")
                }
            } else {
                println("No hay estudiantes en esta materia.")
            }
        }
    }

    // Metodo para actualizar el archivo de materias
    private fun actualizarArchivoMaterias() {
        val file = File(fileNameMaterias)
        file.writeText("")  // Limpiar archivo
        materias.forEach {
            file.appendText("${it.toFileFormat()}\n")
        }
    }

    // Metodo para actualizar el archivo de estudiantes
    private fun actualizarArchivoEstudiantes() {
        val file = File(fileNameEstudiantes)
        file.writeText("")  // Limpiar archivo
        estudiantes.forEach {
            file.appendText("${it.toFileFormat()}\n")
        }
    }

    // Función para guardar datos en los archivos
    private fun guardarEnArchivo(fileName: String, data: String) {
        val file = File(fileName)
        file.appendText("$data\n")
    }
}