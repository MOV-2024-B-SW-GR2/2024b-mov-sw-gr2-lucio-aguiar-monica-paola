package com.example.estudiantemateria

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.estudiantemateria.Models.Estudiante
import com.example.estudiantemateria.Models.Materia
import com.google.android.material.snackbar.Snackbar

class AEstudiantesLista : AppCompatActivity() {
    lateinit var dbHelper: ESqliteHelper
    private var materiaSelected: Materia? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aestudiantes_lista)

        // Get the Materia object from the intent
        materiaSelected = intent.getParcelableExtra("materia")

        if (materiaSelected != null) {
            dbHelper = ESqliteHelper.getInstance(this)
            inicializarRecyclerView()

            val textViewMateriaName = findViewById<TextView>(R.id.tv_nombre_materia)
            textViewMateriaName.text = materiaSelected?.nombre

            val btnCreateStudent = findViewById<Button>(R.id.btn_anadir_estudiante)
            btnCreateStudent.setOnClickListener {
                showAddEstudianteDialog()
            }
        } else {
            finish()
        }
    }

    private fun showAddEstudianteDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_estudiante, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Agregar Estudiante")

        val alertDialog = builder.show()

        val etEstudianteName: EditText = dialogView.findViewById(R.id.et_estudiante_name_add)
        val etFechaNacimiento: EditText = dialogView.findViewById(R.id.et_fecha_nacimiento_add)
        val etCarrera: EditText = dialogView.findViewById(R.id.et_carrera_add)
        val etIRA: EditText = dialogView.findViewById(R.id.et_ira_add)
        val btnAddEstudiante: Button = dialogView.findViewById(R.id.btn_add_estudiante)

        btnAddEstudiante.setOnClickListener {
            val nombre = etEstudianteName.text.toString()
            val fechaNacimiento = etFechaNacimiento.text.toString()
            val carrera = etCarrera.text.toString()
            val ira = etIRA.text.toString().toDoubleOrNull()

            if (nombre.isNotEmpty() && fechaNacimiento.isNotEmpty() && carrera.isNotEmpty() && ira != null) {
                materiaSelected?.id?.let { idMateria ->
                    val nuevoEstudiante = Estudiante(0, nombre, fechaNacimiento, carrera, ira, idMateria)
                    val resultado = dbHelper.crearEstudiante(nuevoEstudiante)

                    if (resultado) {
                        Toast.makeText(this, "Estudiante agregado", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                        actualizarRecyclerView()
                    } else {
                        Toast.makeText(this, "Error al agregar estudiante", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showEditEstudianteDialog(estudiante: Estudiante, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_estudiante, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Editar Estudiante")

        val alertDialog = builder.show()

        val etEstudianteName: EditText = dialogView.findViewById(R.id.et_estudiante_name_add)
        val etFechaNacimiento: EditText = dialogView.findViewById(R.id.et_fecha_nacimiento_add)
        val etCarrera: EditText = dialogView.findViewById(R.id.et_carrera_add)
        val etIRA: EditText = dialogView.findViewById(R.id.et_ira_add)
        val btnUpdateEstudiante: Button = dialogView.findViewById(R.id.btn_add_estudiante)

        // Pre-fill the form with the current details
        etEstudianteName.setText(estudiante.nombre)
        etFechaNacimiento.setText(estudiante.fechaNacimiento)
        etCarrera.setText(estudiante.carrera)
        etIRA.setText(estudiante.IRA.toString())
        btnUpdateEstudiante.text = "Actualizar"

        btnUpdateEstudiante.setOnClickListener {
            val nombre = etEstudianteName.text.toString()
            val fechaNacimiento = etFechaNacimiento.text.toString()
            val carrera = etCarrera.text.toString()
            val ira = etIRA.text.toString().toDoubleOrNull()

            if (nombre.isNotEmpty() && fechaNacimiento.isNotEmpty() && carrera.isNotEmpty() && ira != null) {
                val updatedEstudiante = Estudiante(estudiante.codUnico, nombre, fechaNacimiento, carrera, ira, estudiante.idMateria)
                val resultado = dbHelper.actualizarEstudiante(estudiante.codUnico, updatedEstudiante)

                if (resultado) {
                    Toast.makeText(this, "Estudiante actualizado", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                    actualizarRecyclerView()
                } else {
                    Toast.makeText(this, "Error al actualizar estudiante", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inicializarRecyclerView() {
        recyclerView = findViewById(R.id.rv_estudiantes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        actualizarRecyclerView()
    }

    private fun actualizarRecyclerView() {
        val listaEstudiantes = materiaSelected?.id?.let { dbHelper.obtenerEstudiantesPorMateriaId(it) }
        if (listaEstudiantes != null) {
            val adaptador = FRecyclerViewAdaptadorEstudiante(this, listaEstudiantes.toMutableList(), recyclerView)
            recyclerView.adapter = adaptador
            adaptador.notifyDataSetChanged()
        }
    }
}
