package com.example.proyecto_ruleta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_ruleta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val optionsList = mutableListOf<String>()
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Configurar botones con ViewBinding
        binding.btnAddOption.setOnClickListener {
            addOptionField()
        }

        binding.btnGenerateRoulette.setOnClickListener {
            createRoulette()
        }

        binding.btnViewSavedRoulettes.setOnClickListener {
            startActivity(Intent(this, SavedRoulettesActivity::class.java))
        }
    }

    private fun addOptionField() {
        val editText = android.widget.EditText(this).apply {
            hint = "Opción ${binding.optionsContainer.childCount + 1}"
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        binding.optionsContainer.addView(editText)
    }

    private fun createRoulette() {
        optionsList.clear()
        for (i in 0 until binding.optionsContainer.childCount) {
            val child = binding.optionsContainer.getChildAt(i) as? android.widget.EditText
            child?.text?.toString()?.takeIf { it.isNotEmpty() }?.let {
                optionsList.add(it)
            }
        }

        if (optionsList.size > 1) {
            val rouletteName = binding.etRouletteName.text.toString().trim()
            if (rouletteName.isEmpty()) {
                android.widget.Toast.makeText(this, "Ingresa un nombre para la ruleta", android.widget.Toast.LENGTH_SHORT).show()
                return
            }

            dbHelper.saveRoulette(rouletteName, optionsList)

            val intent = Intent(this, RouletteActivity::class.java).apply {
                putStringArrayListExtra("OPTIONS", ArrayList(optionsList))
            }
            startActivity(intent)
        } else {
            android.widget.Toast.makeText(this, "Agrega al menos 2 opciones", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}
