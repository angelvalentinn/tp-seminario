package com.example.trabajopractico

import android.R.id
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoritasActivity : AppCompatActivity() {

    private lateinit var peliculaAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieDao: MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritas)
        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false) //Elimina el titulo de la toolbar
        recyclerView = findViewById(R.id.recyclerViewFavoritas)
        peliculaAdapter = MovieAdapter(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = peliculaAdapter

        // Cargar películas favoritas
        obtenerFavoritasDesdeBD()
    }

    private fun obtenerFavoritasDesdeBD() {
        lifecycleScope.launch {
            try {
                val peliculas = withContext(Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext).movieDao().getAllMovies()
                }
                
                peliculaAdapter = MovieAdapter(peliculas.toMutableList(), this@FavoritasActivity)
                recyclerView.adapter = peliculaAdapter
            } catch (e: Exception) {
                // Manejo de errores
                Log.e("FavoritasActivity", "Error al obtener las películas: ${e.message}")
            }
        }
    }

}