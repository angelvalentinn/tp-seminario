package com.example.trabajopractico

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var tvRegistrate: TextView
    private lateinit var btnIniciarSesion: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var ivBack: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var notificaciones: Notificaciones

    lateinit var tvServicioRest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        notificaciones = Notificaciones()
        notificaciones.createNotificationChannel(this)
        inicializarComponentes()
        recordarUsuario()
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

            lifecycleScope.launch(Dispatchers.IO) {
                if (verificarCredenciales(email, contraseña)) {
                    withContext(Dispatchers.Main) {
                        if (checkBox.isChecked) {
                            var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                            preferencias.edit().putString(resources.getString(R.string.email_usuario), email).apply()
                            preferencias.edit().putString(resources.getString(R.string.password_usuario), contraseña).apply()
                            notificaciones.mostrarNotificacion(this@MainActivity)
                            //Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(this@MainActivity, ListadoPeliculasActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private suspend fun verificarCredenciales(email: String, contraseña: String): Boolean {
        val user = AppDatabase.getDatabase(applicationContext).userDao().getByEmail(email)
        return user?.password == contraseña
    }

    private fun recordarUsuario() {

        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        val emailGuardado = preferencias.getString(resources.getString(R.string.email_usuario), null)
        val passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), null)


        if (emailGuardado!= null && passwordGuardado!= null) {
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            startMainActivity(emailGuardado) // Automatically log in
        }
    }

    private fun startMainActivity(email: String) {
        val intent = Intent(this, ListadoPeliculasActivity::class.java)
        intent.putExtra(resources.getString(R.string.email_usuario), email)
        startActivity(intent)
        finish()

    }
}

