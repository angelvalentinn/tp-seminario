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
    var movies: MutableList<Result<MovieEntity>>,  // Usamos Result<MovieEntity> aquí
    var context: Context
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtTitulo: TextView
        var txtFecha: TextView
        var txtCalificacion: TextView

        init {
            txtTitulo = view.findViewById(R.id.tv_titulo)
            txtFecha = view.findViewById(R.id.tv_fecha)
            txtCalificacion = view.findViewById(R.id.tv_calificacion)
            txtCalificacion.text = "Calificación:"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pelicula, parent, false)  // Asegúrate de que este layout exista
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movies[position]
        if (item is Result.Success) {  // Verifica si el resultado es exitoso
            val movie = item.data
            holder.txtTitulo.text = movie.title


        }
    }

    fun updateMovies(favoriteMovies: List<MovieEntity>) {
        movies.clear()  // Limpia la lista actual

        for (movie in favoriteMovies) {
            val result = Result.Success(movie)  // Crea un Result.Success para cada MovieEntity
            movies.add(result)
        }

        notifyDataSetChanged()  // Notifica al adaptador que los datos han cambiado
    }
}