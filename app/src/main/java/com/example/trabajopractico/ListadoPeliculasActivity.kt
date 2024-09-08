package com.example.trabajopractico

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListadoPeliculasActivity : AppCompatActivity() {

    private lateinit var rvPeliculas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_peliculas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarRecyclerView()

    }

    private fun getPeliculas(): MutableList<Pelicula> {

        val peliculas: MutableList<Pelicula> = ArrayList()

        peliculas.add(Pelicula(1, "Spider-Man: No Way Home", "2021-12-15",9.5))
        peliculas.add(Pelicula(2, "Black Widow", "2021-07-09",7.8))
        peliculas.add(Pelicula(3, "The Batman", "2022-03-04",9.8))

        return peliculas

    }

    private fun inicializarRecyclerView() {
        rvPeliculas = findViewById(R.id.rvPeliculas)
        rvPeliculas.layoutManager = LinearLayoutManager(this)
        rvPeliculas.adapter =  PeliculaAdapter(getPeliculas(), this)
    }
}