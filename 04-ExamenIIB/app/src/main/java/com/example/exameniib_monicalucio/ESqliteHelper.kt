package com.example.exameniib_monicalucio

import android.content.ContentValues
import com.example.exameniib_monicalucio.Models.*;
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelper(context: Context) : SQLiteOpenHelper(
    context,
    "moviles",
    null,
    5
) {

    init {
        // Ensure the context is the application context to avoid memory leaks
        if (INSTANCE == null) {
            INSTANCE = this
        }
    }

    companion object {
        private var INSTANCE: ESqliteHelper? = null

        fun getInstance(context: Context): ESqliteHelper {
            if (INSTANCE == null) {
                synchronized(ESqliteHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = ESqliteHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaMateria =
            """
            CREATE TABLE MATERIA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                codigo VARCHAR(50),
                estado BOOLEAN,
                codigoProfesor VARCHAR(10)
            )
        """.trimIndent()

        val scriptSQLCrearTablaEstudiante =
            """
    CREATE TABLE ESTUDIANTE(
        codUnico INTEGER PRIMARY KEY AUTOINCREMENT,
        nombre VARCHAR(50),
        fechaNacimiento DATE,
        carrera VARCHAR(30),
        IRA DOUBLE,
        idMateria INTEGER,
        latitud DOUBLE,
        longitud DOUBLE,
        FOREIGN KEY (idMateria) REFERENCES Materia(id)
    )
    """.trimIndent()

        db?.execSQL(scriptSQLCrearTablaMateria)
        db?.execSQL(scriptSQLCrearTablaEstudiante)

        // Insertar 5 elementos en la tabla MATERIA
        val insertMaterias = """
    INSERT INTO MATERIA (nombre, codigo, estado, codigoProfesor) VALUES
    ('Redes', 'RED101', 1, 'PROF006'),
    ('Profesionalismo Informático', 'PROF101', 1, 'PROF007'),
    ('Desarrollo de Juegos', 'JUE101', 1, 'PROF008'),
    ('Algoritmos', 'ALG101', 1, 'PROF009'),
    ('Programación', 'PROG101', 1, 'PROF010');
    """.trimIndent()

        db?.execSQL(insertMaterias)
        Log.d("DB_DEBUG", "Materias insertadas correctamente")

        // Insertar estudiantes en la tabla ESTUDIANTE
        val insertEstudiantes = """
    INSERT INTO ESTUDIANTE (nombre, fechaNacimiento, carrera, IRA, idMateria, latitud, longitud) VALUES
        ('Karina Ortiz', '2001-01-15', 'Ingeniería', 4.5, 1, -0.172097, -78.475376), 
        ('María Loma', '2002-02-20', 'Ciencias', 4.2, 2, -0.268127, -78.536933), 
        ('Monica Aguiar', '2000-03-30', 'Química', 3.8, 3, -0.933054, -78.615748), 
        ('Ana Villena', '1999-04-10', 'Biología', 4.7, 4, -0.327654, -78.183406),  
        ('Luis Castro', '2001-05-25', 'Historia', 3.9, 5, -0.895732, -78.234590); 
    """.trimIndent()

        db?.execSQL(insertEstudiantes)
        Log.d("DB_DEBUG", "Estudiantes insertados correctamente")
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MATERIA")
        db?.execSQL("DROP TABLE IF EXISTS ESTUDIANTE")
        onCreate(db)
    }
    // Métodos CRUD para MATERIA
    fun crearMateria(materia: Materia): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", materia.nombre)
        valoresAGuardar.put("codigo", materia.codigo)
        valoresAGuardar.put("estado", materia.estado)
        valoresAGuardar.put("codigoProfesor", materia.codigoProfesor)
        val resultadoGuardar = basedatosEscritura.insert("MATERIA", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun actualizarMateria(id: Int, materia: Materia): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", materia.nombre)
        valoresAActualizar.put("codigo", materia.codigo)
        valoresAActualizar.put("estado", materia.estado)
        valoresAActualizar.put("codigoProfesor", materia.codigoProfesor)
        val resultadoActualizar = basedatosEscritura.update("MATERIA", valoresAActualizar, "id=?", arrayOf(id.toString()))
        basedatosEscritura.close()
        return resultadoActualizar != -1
    }

    fun borrarMateria(id: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val resultadoBorrar = basedatosEscritura.delete("MATERIA", "id=?", arrayOf(id.toString()))
        basedatosEscritura.close()
        return resultadoBorrar != -1
    }

    fun obtenerTodasMaterias(): MutableList<Materia> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM MATERIA"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val listaMaterias = mutableListOf<Materia>()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val materia = Materia(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getInt(3) != 0,
                    resultadoConsultaLectura.getString(4)
                )
                listaMaterias.add(materia)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaMaterias
    }

    fun obtenerMateriaPorId(id: Int): Materia? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM MATERIA WHERE id = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, arrayOf(id.toString()))
        val materia: Materia? = if (resultadoConsultaLectura.moveToFirst()) {
            Materia(
                resultadoConsultaLectura.getInt(0),
                resultadoConsultaLectura.getString(1),
                resultadoConsultaLectura.getString(2),
                resultadoConsultaLectura.getInt(3) != 0,
                resultadoConsultaLectura.getString(4)
            )
        } else {
            null
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return materia
    }

    // Métodos CRUD para ESTUDIANTE
    fun crearEstudiante(estudiante: Estudiante): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",estudiante.nombre)
        valoresAGuardar.put("fechaNacimiento", estudiante.fechaNacimiento)
        valoresAGuardar.put("carrera", estudiante.carrera)
        valoresAGuardar.put("IRA", estudiante.IRA)
        valoresAGuardar.put("idMateria", estudiante.idMateria)
        valoresAGuardar.put("latitud", estudiante.latitud)
        valoresAGuardar.put("longitud",estudiante.longitud)
        val resultadoGuardar = basedatosEscritura.insert("ESTUDIANTE", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun actualizarEstudiante(codUnico: Int, estudiante: Estudiante): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", estudiante.nombre)
        valoresAActualizar.put("fechaNacimiento", estudiante.fechaNacimiento)
        valoresAActualizar.put("carrera", estudiante.carrera)
        valoresAActualizar.put("IRA", estudiante.IRA)
        valoresAActualizar.put("idMateria", estudiante.idMateria)
        valoresAActualizar.put("latitud", estudiante.latitud)
        valoresAActualizar.put("longitud",estudiante.longitud)

        val resultadoActualizar = basedatosEscritura.update("ESTUDIANTE", valoresAActualizar, "codUnico=?", arrayOf(codUnico.toString()))
        basedatosEscritura.close()
        return resultadoActualizar != -1
    }

    fun borrarEstudiante(codUnico: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val resultadoBorrar = basedatosEscritura.delete("ESTUDIANTE", "codUnico=?", arrayOf(codUnico.toString()))
        basedatosEscritura.close()
        return resultadoBorrar != -1
    }

    fun obtenerTodosEstudiantes(): List<Estudiante> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ESTUDIANTE"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val listaEstudiantes = mutableListOf<Estudiante>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaNacimiento = resultadoConsultaLectura.getString(2)
                val carrera = resultadoConsultaLectura.getString(3)
                val ira = resultadoConsultaLectura.getDouble(4)
                val idMateria = resultadoConsultaLectura.getInt(5)
                val latitud = resultadoConsultaLectura.getDouble(6)
                val longitud = resultadoConsultaLectura.getDouble(7)

                val estudiante = Estudiante(id, nombre, fechaNacimiento, carrera, ira, idMateria,latitud, longitud)
                listaEstudiantes.add(estudiante)

                // Log each student retrieved
                Log.d("DB_DEBUG", "Estudiante: $estudiante")
            } while (resultadoConsultaLectura.moveToNext())
        } else {
            // Log if no students were found
            Log.d("DB_DEBUG", "No se encontraron estudiantes")
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaEstudiantes
    }

    fun obtenerEstudiantesPorMateriaId(idMateria: Int): MutableList<Estudiante> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ESTUDIANTE WHERE idMateria = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, arrayOf(idMateria.toString()))

        val listaEstudiantes = mutableListOf<Estudiante>()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val estudiante = Estudiante(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getString(3),
                    resultadoConsultaLectura.getDouble(4),
                    resultadoConsultaLectura.getInt(5),
                    resultadoConsultaLectura.getDouble(6),
                    resultadoConsultaLectura.getDouble(7)
                )
                listaEstudiantes.add(estudiante)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaEstudiantes
    }

}

