package com.example.trabajopractico

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class presentacion : AppCompatActivity() {

    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_presentacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarComponentes()
        navegarARegistro()
        navegarAInicioSesion()
        navegarATerminos()
    }

    private fun inicializarComponentes() {
        btnIniciarSesion = findViewById(R.id.btnIniciarSesionP)
        btnRegistrarse = findViewById(R.id.btnRegistrarseP)
    }

    private fun navegarARegistro() {
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, activityRegister::class.java)
            startActivity(intent)
        }
    }

    private fun navegarAInicioSesion() {
        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navegarATerminos() {
        findViewById<Button>(R.id.btnTerminos).setOnClickListener {

            val fragment = terminosFragmento()


            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}