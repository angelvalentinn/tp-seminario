package com.example.trabajopractico
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class ActivityDetalle: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)

        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val titulo = intent.getStringExtra("titulo")
        val sinopsis = intent.getStringExtra("overview");
        val imagen = intent.getStringExtra("imagen");

        val tvTitulo: TextView = findViewById(R.id.tv_tituloDetalle)
        val tvSinopsis: TextView = findViewById(R.id.tvSinopsis);
        val ivImagen: ImageView = findViewById(R.id.ivImagenPelicula);

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

}