package com.example.proyecto_ruleta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RouletteAdapter(
    private val roulettes: List<Pair<Int, String>>, // Corregido el nombre
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<RouletteAdapter.RouletteViewHolder>() {

    class RouletteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRouletteName: TextView = view.findViewById(R.id.tvRouletteName)
        val cardView: CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouletteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_roulette, parent, false)
        return RouletteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouletteViewHolder, position: Int) {
        val (id, name) = roulettes[position]
        holder.tvRouletteName.text = name

        holder.cardView.setOnClickListener {
            onClick(id)
        }
    }

    override fun getItemCount(): Int = roulettes.size
}
