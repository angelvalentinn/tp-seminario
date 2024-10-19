package com.example.trabajopractico

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.data.model.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListadoPeliculasActivity : AppCompatActivity() {

    private lateinit var rvPeliculas: RecyclerView
    private lateinit var adapter: PeliculaAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_peliculas)
        progressBar = findViewById(R.id.progressBar)

        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        inicializarRecyclerView()
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false) //Elimina el titulo de la toolbar
        getPeliculasApi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crear_pelicula, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_pelicula -> {
                val intent = Intent(this, CrearPeliculaActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun getPeliculasApi() {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.retrofitService.getPeliculas()
                }
                progressBar.visibility = View.GONE
                val peliculas = response.results
                adapter = PeliculaAdapter(peliculas.toMutableList(), this@ListadoPeliculasActivity)
                rvPeliculas.adapter = adapter

            } catch (e: Exception) {
                Log.e("API Error", "Error fetching peliculas: ${e.message}")
            }
        }
    }

    private fun inicializarRecyclerView() {
        rvPeliculas = findViewById(R.id.rvPeliculas)
        rvPeliculas.layoutManager = LinearLayoutManager(this)
        adapter = PeliculaAdapter(mutableListOf(), this)
        rvPeliculas.adapter = adapter
    }
}