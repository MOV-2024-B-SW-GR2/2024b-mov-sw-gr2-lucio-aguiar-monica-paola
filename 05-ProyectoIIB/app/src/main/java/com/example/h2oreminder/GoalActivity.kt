package com.example.h2oreminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        val seekBar = findViewById<SeekBar>(R.id.goal_seekbar)
        val goalValue = findViewById<TextView>(R.id.goal_value)
        val btnSave = findViewById<Button>(R.id.btn_save_goal)

        seekBar.max = 5000

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                goalValue.text = "$progress ml"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnSave.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}
