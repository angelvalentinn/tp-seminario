package com.example.trabajopractico

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var tvRegistrate: TextView
    private lateinit var btnIniciarSesion: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var ivBack: ImageView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarComponentes()
        navegarARegistro()
        manejarInicioSesion()
    }

    private fun inicializarComponentes() {
        tvRegistrate = findViewById(R.id.tvRegistrate)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        checkBox = findViewById(R.id.myCheckBox)
        ivBack = findViewById(R.id.ivBack)
        toolbar = findViewById(R.id.toolbar)
    }

    private fun navegarARegistro() {
        tvRegistrate.setOnClickListener {
            val intent = Intent(this, activityRegister::class.java)
            startActivity(intent)
        }
    }

    private fun manejarInicioSesion() {
        btnIniciarSesion.setOnClickListener {
            val email = etEmail.text.toString()
            val contraseña = etPassword.text.toString()

            if (verificarCredenciales(email, contraseña)) {
                val intent = Intent(this, ListadoPeliculasActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verificarCredenciales(email: String, contraseña: String): Boolean {
        val user = AppDatabase.getDatabase(applicationContext).userDao().getByEmail(email)
        return user?.password == contraseña
    }
}