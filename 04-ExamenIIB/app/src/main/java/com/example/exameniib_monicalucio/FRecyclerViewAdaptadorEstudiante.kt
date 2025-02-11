package com.example.exameniib_monicalucio

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib_monicalucio.Models.Estudiante

class FRecyclerViewAdaptadorEstudiante(
    private val contexto: AEstudiantesLista,
    private var lista: MutableList<Estudiante>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<FRecyclerViewAdaptadorEstudiante.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.tv_estudiante_nombre)
        val codigoUnicoTextView: TextView = view.findViewById(R.id.tv_estudiante_codigo)
        val carreraTextView: TextView = view.findViewById(R.id.tv_estudiante_carrera)
        val iraTextView: TextView = view.findViewById(R.id.tv_estudiante_ira)
        val fechaTextView: TextView = view.findViewById(R.id.tv_estudiante_fecha)
        val botonOpciones: ImageButton = view.findViewById(R.id.btn_more_options_estudiante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.estudiante_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val estudianteActual = lista[position]
        holder.nombreTextView.text = estudianteActual.nombre
        holder.codigoUnicoTextView.text = estudianteActual.codUnico.toString()
        holder.carreraTextView.text = estudianteActual.carrera
        holder.iraTextView.text = estudianteActual.IRA.toString()
        holder.fechaTextView.text = estudianteActual.fechaNacimiento
        holder.botonOpciones.setOnClickListener { view ->
            showPopupMenu(view, position)
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_estudiante_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    contexto.showEditEstudianteDialog(lista[position], position)
                    true
                }
                R.id.action_delete -> {
                    val estudiante = lista[position]
                    contexto.dbHelper.borrarEstudiante(estudiante.codUnico)
                    lista.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, lista.size)
                    Toast.makeText(contexto, "Estudiante eliminado", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_address -> {
                    val estudiante = lista[position]
                    contexto.showEstudianteLocationDialog(estudiante)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    fun updateData(newLista: List<Estudiante>) {
        lista = newLista.toMutableList()
        notifyDataSetChanged()
    }
}