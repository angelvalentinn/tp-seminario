package com.example.trabajopractico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GeneroAdapter(var generos: List<Genero>, var context: Context) :
    RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>() {

    class GeneroViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView

        init {
            txtName = view.findViewById(R.id.tvNameGenero)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GeneroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genero, parent, false)
        return GeneroViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val item = generos[position]
        holder.txtName.text = item.name
    }

    override fun getItemCount(): Int = generos.size

}