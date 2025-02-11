package com.example.estudiantemateria

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.estudiantemateria.Models.*
import android.app.AlertDialog
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
class AMateriasLista : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView;
    public lateinit var dbHelper: ESqliteHelper;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_amaterias_lista)

        // Initialize the database helper
        dbHelper = ESqliteHelper.getInstance(this)

        val btn_anadir_materia = findViewById<Button>(R.id.btn_anadir_materia)
        btn_anadir_materia
            .setOnClickListener{
                showAddMateriaDialog()
            }

        inicializarRecyclerView()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun inicializarRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.rv_materias)
        val adaptador = FRecyclerViewAdaptadorMateria(
            this,
            dbHelper.obtenerTodasMaterias(),
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    private fun showAddMateriaDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_materia, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Agregar Materia")

        val alertDialog = builder.show()

        val etMateriaName: EditText = dialogView.findViewById(R.id.et_materia_name_add)
        val etMateriaCode: EditText = dialogView.findViewById(R.id.et_materia_code_add)
        val etProfessorCode: EditText = dialogView.findViewById(R.id.et_professor_code_add)
        val cbMateriaActive: CheckBox = dialogView.findViewById(R.id.cb_materia_active_add)
        val btnAddMateria: Button = dialogView.findViewById(R.id.btn_add_materia)

        btnAddMateria.setOnClickListener {
            val nombre = etMateriaName.text.toString()
            val codigo = etMateriaCode.text.toString()
            val codigoProfesor = etProfessorCode.text.toString()
            val estado = cbMateriaActive.isChecked

            if (nombre.isNotEmpty() && codigo.isNotEmpty() && codigoProfesor.isNotEmpty()) {
                val nuevaMateria = Materia(0, nombre, codigo, estado, codigoProfesor)
                val resultado = dbHelper.crearMateria(nuevaMateria)

                if (resultado) {
                    Toast.makeText(this, "Materia agregada", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                    actualizarRecyclerView()
                } else {
                    Toast.makeText(this, "Error al agregar materia", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showEditMateriaDialog(materia: Materia, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_materia, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Editar Materia")

        val alertDialog = builder.show()

        val etMateriaName: EditText = dialogView.findViewById(R.id.et_materia_name_edit)
        val etMateriaCode: EditText = dialogView.findViewById(R.id.et_materia_code_edit)
        val etProfessorCode: EditText = dialogView.findViewById(R.id.et_professor_code_edit)
        val cbMateriaActive: CheckBox = dialogView.findViewById(R.id.cb_materia_active_edit)
        val btnUpdateMateria: Button = dialogView.findViewById(R.id.btn_update_materia)

        // Pre-fill the form with the current details
        etMateriaName.setText(materia.nombre)
        etMateriaCode.setText(materia.codigo)
        etProfessorCode.setText(materia.codigoProfesor)
        cbMateriaActive.isChecked = materia.estado

        btnUpdateMateria.setOnClickListener {
            val nombre = etMateriaName.text.toString()
            val codigo = etMateriaCode.text.toString()
            val codigoProfesor = etProfessorCode.text.toString()
            val estado = cbMateriaActive.isChecked

            if (nombre.isNotEmpty() && codigo.isNotEmpty() && codigoProfesor.isNotEmpty()) {
                val updatedMateria = Materia(materia.id, nombre, codigo, estado, codigoProfesor)
                val dbHelper = ESqliteHelper.getInstance(this)
                val resultado = dbHelper.actualizarMateria(materia.id, updatedMateria)

                if (resultado) {
                    Toast.makeText(this, "Materia actualizada", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                    actualizarRecyclerView()
                } else {
                    Toast.makeText(this, "Error al actualizar materia", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarRecyclerView() {
        val dbHelper = ESqliteHelper.getInstance(this)
        val listaMaterias = dbHelper.obtenerTodasMaterias()
        (recyclerView.adapter as FRecyclerViewAdaptadorMateria).updateData(listaMaterias)
    }

    fun irActividad(
        clase: Class<*>,
        materia: Materia?
    ){
        val intent = Intent(this, clase)
        intent.putExtra("materia",materia)
        startActivity(intent)
    }
}