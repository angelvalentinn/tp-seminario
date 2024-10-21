package com.example.trabajopractico
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabajopractico.data.model.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityDetalle: AppCompatActivity() {

    private lateinit var rvActores: RecyclerView;
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var rvGeneros: RecyclerView;
    private lateinit var generoAdapter: GeneroAdapter
    private lateinit var movieDao: MovieDao
    private lateinit var ivFavorito: ImageView
    private var isFavorito: Boolean = false
    private var idPelicula: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)

        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val db = AppDatabase.getDatabase(this)
        movieDao = db.movieDao()


        val titulo = intent.getStringExtra("titulo")
        val sinopsis = intent.getStringExtra("overview");
        val imagen = intent.getStringExtra("imagen");
        val idPelicula = intent.getStringExtra("idPelicula");

        val tvTitulo: TextView = findViewById(R.id.tv_tituloDetalle)
        val tvSinopsis: TextView = findViewById(R.id.tvSinopsis);
        val ivImagen: ImageView = findViewById(R.id.ivImagenPelicula);
        ivFavorito = findViewById(R.id.ivFavorito)

        tvTitulo.text = titulo
        tvSinopsis.text = sinopsis
        Glide.with(this)
            .load(imagen)
            .into(ivImagen)

        inicializarRecyclerViewActores()
        inicializarRecyclerViewGeneros()

        idPelicula?.let {
            checkIfMovieIsFavorite(it)
        }

        ivFavorito.setOnClickListener {
            idPelicula?.let { id ->
                toggleFavorite(id, titulo ?: "", sinopsis ?: "", imagen ?: "")
            }
        }

        if(idPelicula != null )  {
            getActoresApi(idPelicula.toString())
            getGenerosApi(idPelicula.toString())
        }

        tvTitulo.text = titulo
        tvSinopsis.text = sinopsis
        Glide.with(this)
            .load(imagen)
            .into(ivImagen)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun inicializarRecyclerViewActores() {
        rvActores = findViewById(R.id.rvActores)
        rvActores.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actorAdapter = ActorAdapter(mutableListOf(), this)
        rvActores.adapter = actorAdapter
    }

    fun inicializarRecyclerViewGeneros() {
        rvGeneros = findViewById(R.id.rvGeneros)
        rvGeneros.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        generoAdapter = GeneroAdapter(mutableListOf(), this)
        rvGeneros.adapter = generoAdapter
    }

    private fun getActoresApi(idPelicula : String) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.retrofitService.getActores(idPelicula)
                }

                val actores = response.cast
                actorAdapter = ActorAdapter(actores.toMutableList(), this@ActivityDetalle)
                rvActores.adapter = actorAdapter

            } catch (e: Exception) {
                Log.e("API Error", "Error fetching actores: ${e.message}")
            }
        }
    }

    private fun getGenerosApi(idPelicula : String) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.retrofitService.getDetalle(idPelicula)
                }
                val generos = response.genres
                generoAdapter = GeneroAdapter(generos.toMutableList(), this@ActivityDetalle)
                rvGeneros.adapter = generoAdapter

            } catch (e: Exception) {
                Log.e("API Error", "Error fetching generos: ${e.message}")
            }
        }
    }

    private fun checkIfMovieIsFavorite(idPelicula: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = movieDao.getMovieById(idPelicula.toInt())
            withContext(Dispatchers.Main) {
                isFavorito = movie != null
                updateFavoriteIcon(isFavorito)
            }
        }
    }

    private fun toggleFavorite(idPelicula: String, titulo: String, sinopsis: String, imagen: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (isFavorito) {
                movieDao.deleteMovieById(idPelicula.toInt())
                withContext(Dispatchers.Main) {
                    isFavorito = false
                    updateFavoriteIcon(isFavorito)
                    Toast.makeText(this@ActivityDetalle, "Película eliminada de favoritos", Toast.LENGTH_SHORT).show()
                }
            } else {
                val movie = MovieEntity(idPelicula.toInt(), titulo, sinopsis, imagen)
                movieDao.insertMovie(movie)
                withContext(Dispatchers.Main) {
                    isFavorito = true
                    updateFavoriteIcon(isFavorito)
                    Toast.makeText(this@ActivityDetalle, "Película agregada a favoritos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateFavoriteIcon(isFavorito: Boolean) {
        if (isFavorito) {
            ivFavorito.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            ivFavorito.setImageResource(R.drawable.ic_favorite_border)
        }
    }

}