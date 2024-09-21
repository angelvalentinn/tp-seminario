package com.example.trabajopractico

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliculaAdapter (var peliculas: MutableList<Pelicula>, var context: Context):

    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>(){

    class PeliculaViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var txtTitulo: TextView
        var txtFecha: TextView
        var txtCalificacion: TextView
        val btnVerMas: Button = itemView.findViewById(R.id.btnVerMas)

        init {
            txtTitulo = view.findViewById(R.id.tv_titulo)
            txtFecha = view.findViewById(R.id.tv_fecha)
            txtCalificacion = view.findViewById(R.id.tv_calificacion)
            txtCalificacion.text = "Calificación:"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(view)
    }

    override fun getItemCount() = peliculas.size

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val item = peliculas.get(position)
        holder.txtTitulo.text = item.titulo
        holder.txtFecha.text = "Fecha de Estreno: ${item.fecha}"
        holder.txtCalificacion.text = "Calificación: ${item.calificacion}"

        holder.btnVerMas.setOnClickListener {
            val intent = Intent(context, ActivityDetalle::class.java)

            intent.putExtra("titulo", item.titulo)
            // Paso solo el titulo porque en el detalle
            // no hay apartado para calificacion ni fecha
            context.startActivity(intent)
        }

    }


}