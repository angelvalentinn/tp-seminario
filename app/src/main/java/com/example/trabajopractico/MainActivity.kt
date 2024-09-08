package com.example.trabajopractico

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvRegistrate: TextView
    private lateinit var btnIniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarComponentes()
        navegarARegistro()
        navegarAListadoPeliculas()

    }

    private fun inicializarComponentes() {
        tvRegistrate = findViewById((R.id.tvRegistrate))
        btnIniciarSesion = findViewById((R.id.btnIniciarSesion))
    }

    private fun navegarARegistro() {
        tvRegistrate.setOnClickListener {
            val intent = Intent(this, activityRegister::class.java);
            startActivity(intent)
            finish()
        }
    }

    private fun navegarAListadoPeliculas() {
        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, ListadoPeliculasActivity::class.java);
            startActivity(intent)
            finish()
        }
    }
}