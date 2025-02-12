package com.example.proyecto_ruleta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_ruleta.databinding.ActivitySavedRoulettesBinding

class SavedRoulettesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedRoulettesBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivitySavedRoulettesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        val roulettes: List<Pair<Int, String>> = dbHelper.getAllRoulettes() // 🔹 Asegurar que es una lista de pares (ID, Nombre)

        // Configurar RecyclerView
        binding.recyclerViewRoulettes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRoulettes.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recyclerViewRoulettes.adapter = RouletteAdapter(roulettes) { id ->  // 🔹 Pasar lista y lambda correctamente
            val options: List<String>? = dbHelper.getRouletteOptions(id) // 🔹 Obtener opciones de la ruleta
            if (options != null) {
                val intent = Intent(this, RouletteActivity::class.java).apply {
                    putStringArrayListExtra("OPTIONS", ArrayList(options))
                }
                startActivity(intent)
            }
        }
    }
}
