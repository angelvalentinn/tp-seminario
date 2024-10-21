package com.example.trabajopractico

import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabajopractico.data.model.Result

import com.example.trabajopractico.MovieEntity

class PeliculaAdapter (var peliculas: MutableList<Result>, var context: Context):

    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>(){

    class PeliculaViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var txtTitulo: TextView
        var txtFecha: TextView
        var txtCalificacion: TextView
        val btnVerMas: Button = itemView.findViewById(R.id.btnVerMas)
        var ivPelicula: ImageView = itemView.findViewById(R.id.iv_pelicula)

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
        val item = peliculas[position]
        holder.txtTitulo.text = item.original_title
        holder.txtFecha.text = "Fecha de Estreno: ${item.release_date}"
        val calificacionFormateada = String.format("%.1f", item.vote_average)
        holder.txtCalificacion.text = "Calificación: $calificacionFormateada"
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/original/${item.poster_path}")
            .placeholder(R.drawable.imagen_fallback)
            .into(holder.ivPelicula)
        holder.btnVerMas.setOnClickListener {
            val intent = Intent(context, ActivityDetalle::class.java)
            intent.putExtra("titulo", item.original_title)
            intent.putExtra("idPelicula", item.id.toString())
            intent.putExtra("overview", item.overview)
            intent.putExtra("imagen", "https://image.tmdb.org/t/p/original/${item.poster_path}")
            context.startActivity(intent)
        }
    }




}


