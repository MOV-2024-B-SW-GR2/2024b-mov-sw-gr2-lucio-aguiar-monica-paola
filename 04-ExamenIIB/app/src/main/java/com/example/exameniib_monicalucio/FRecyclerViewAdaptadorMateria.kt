package com.example.exameniib_monicalucio

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib_monicalucio.Models.Materia

class FRecyclerViewAdaptadorMateria(
    private val contexto: AMateriasLista,
    private var lista: MutableList<Materia>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<FRecyclerViewAdaptadorMateria.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.tv_materia_name)
        val codigoTextView: TextView = view.findViewById(R.id.tv_materia_codigo)
        val profesorIDTextView: TextView = view.findViewById(R.id.tv_materia_profesor)
        val activoTextView: TextView = view.findViewById(R.id.tv_materia_estado)
        val botonOpciones: ImageButton = view.findViewById(R.id.btn_more_options_materia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.materia_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val materiaActual = lista[position]
        holder.nombreTextView.text = materiaActual.nombre
        holder.codigoTextView.text = materiaActual.codigo
        holder.activoTextView.text = if (materiaActual.estado) "Activo" else "Inactivo"
        holder.profesorIDTextView.text = materiaActual.codigoProfesor
        holder.botonOpciones.setOnClickListener { view ->
            showPopupMenu(view, position)
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val materiaActual = lista[position]
        val popupMenu = PopupMenu(view.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_materia_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.action_edit -> {
                    contexto.showEditMateriaDialog(materiaActual, position)
                    true
                }
                R.id.action_delete -> {
                    try {
                        contexto.dbHelper.borrarMateria(materiaActual.id)
                        lista.removeAt(position) // Remove item from the list
                        notifyItemRemoved(position) // Notify adapter of item removal
                        notifyItemRangeChanged(position, lista.size) // Notify adapter of range change
                        Toast.makeText(contexto, "Materia actualizada", Toast.LENGTH_SHORT).show()

                    }catch (e: Exception){
                        Log.d(null, e.message.toString())
                    }
                    true
                }
                R.id.action_see_students -> {
                    contexto.irActividad(AEstudiantesLista::class.java, materiaActual)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
    fun updateData(newLista: MutableList<Materia>) {
        lista = newLista
        notifyDataSetChanged()
    }
}

