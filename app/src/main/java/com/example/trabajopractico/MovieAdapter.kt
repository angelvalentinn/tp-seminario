package com.example.trabajopractico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabajopractico.data.Result

class MovieAdapter(
    var movies: List<MovieEntity>,  // Usamos Result<MovieEntity> aquí
    var context: Context
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titulo: TextView
        var posterPath: ImageView
        var overview: TextView
        var id: TextView

        init {
            titulo = view.findViewById(R.id.tvFavTitulo)
            overview = view.findViewById(R.id.tvFavOverview)
            posterPath = view.findViewById(R.id.ivFavPoster)
            id = view.findViewById(R.id.tvFavId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorito, parent, false)  // Asegúrate de que este layout exista
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movies[position]
        holder.titulo.text = item.title
        holder.overview.text = item.overview
        holder.id.text = item.id.toString()
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/original/${item.posterPath}")
            .placeholder(R.drawable.imagen_fallback)
            .into(holder.posterPath)

    }

}