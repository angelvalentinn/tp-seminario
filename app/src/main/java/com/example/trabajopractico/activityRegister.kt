package com.example.trabajopractico

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class activityRegister : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etContraseña: EditText
    private lateinit var etContraseñaRepetida: EditText
    private lateinit var btnRegistrarse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicializarComponentes()
        registrarUsuario()
    }

    private fun inicializarComponentes() {
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        etEmail = findViewById(R.id.email)
        etContraseña = findViewById(R.id.contraseña)
        etContraseñaRepetida = findViewById(R.id.contraseñaRepetida)
    }

    private fun navegarAIniciarSesion() {
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent)
        finish()
    }

    private fun validarEmail(email: String): Boolean {
        val emailRegex =
            "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}".toRegex()
        return email.matches(emailRegex)
    }

    private fun registrarUsuario() {
        btnRegistrarse.setOnClickListener {

            var email = etEmail.text.toString()
            var contraseña = etContraseña.text.toString()
            var contraseñaRepetida = etContraseñaRepetida.text.toString();

            if (email.isEmpty() || contraseña.isEmpty() || contraseñaRepetida.isEmpty()) {
                Toast.makeText(this, "Es necesario completar todos los datos", Toast.LENGTH_SHORT)
                    .show()

            } else if (contraseña != contraseñaRepetida) {
                Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show()

            } else if (!validarEmail(email)) {
                Toast.makeText(this, "El formato del email es inválido", Toast.LENGTH_SHORT).show()

            } else {
                //Verifico si el usuario ya existe en la BD en otro hilo
                lifecycleScope.launch(Dispatchers.IO) {

                    var user = AppDatabase.getDatabase(applicationContext).userDao().getByEmail(email)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            Toast.makeText(this@activityRegister, "El usuario ya está registrado", Toast.LENGTH_SHORT).show()
                        } else {
                            var nuevoUsuario = User(email, contraseña)
                            withContext(Dispatchers.IO) {
                                AppDatabase.getDatabase(applicationContext).userDao()
                                    .insert(nuevoUsuario)
                            }
                            navegarAIniciarSesion()
                        }
                    }

                }

            }


        }

    }
}