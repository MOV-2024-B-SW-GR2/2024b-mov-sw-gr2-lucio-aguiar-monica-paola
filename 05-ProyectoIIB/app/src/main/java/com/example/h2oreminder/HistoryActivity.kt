package com.example.h2oreminder

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val hydrationTable = findViewById<TableLayout>(R.id.hydration_table)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvPromedio = findViewById<TextView>(R.id.tvPromedio)
        val tvDiasLogrados = findViewById<TextView>(R.id.tvDiasLogrados)

        // Datos de ejemplo (día, consumo en ml, estado)
        val data = listOf(
            Triple("Lunes", 1800, "Pendiente"),
            Triple("Martes", 1800, "Pendiente"),
            Triple("Miércoles", 2000, "Completado"),
            Triple("Jueves", 1500, "Pendiente"),
            Triple("Viernes", 1900, "Pendiente"),
            Triple("Sábado", 2000, "Completado"),
            Triple("Domingo", 2200, "Completado")
        )

        var totalConsumo = 0
        var diasLogrados = 0

        for ((dia, consumo, estado) in data) {
            val row = TableRow(this)

            val diaTextView = TextView(this)
            diaTextView.text = dia
            diaTextView.setPadding(8, 8, 8, 8)
            diaTextView.setBackgroundResource(android.R.color.white)

            val consumoTextView = TextView(this)
            consumoTextView.text = "$consumo ml"
            consumoTextView.setPadding(8, 8, 8, 8)
            consumoTextView.setBackgroundResource(android.R.color.white)

            val estadoTextView = TextView(this)
            estadoTextView.text = estado
            estadoTextView.setPadding(8, 8, 8, 8)
            estadoTextView.setBackgroundResource(android.R.color.white)

            // Cambiar color según estado
            if (estado == "Completado") {
                estadoTextView.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                estadoTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }

            row.addView(diaTextView)
            row.addView(consumoTextView)
            row.addView(estadoTextView)

            hydrationTable.addView(row)

            // Sumar consumo total
            totalConsumo += consumo

            // Contar días logrados
            if (estado == "Completado") {
                diasLogrados++
            }
        }

        // Calcular promedio
        val promedioConsumo = totalConsumo / data.size

        // Actualizar resumen semanal
        tvTotal.text = "Total: $totalConsumo ml"
        tvPromedio.text = "Promedio: $promedioConsumo ml"
        tvDiasLogrados.text = "Días logrados: $diasLogrados/7"
    }
}
