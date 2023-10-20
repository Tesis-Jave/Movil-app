package com.example.javepuntos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val buttonProductos = findViewById<ImageButton>(R.id.buttonProductos)

        buttonProductos.setOnClickListener {
            // Crear un Intent para abrir la segunda actividad
            val intent = Intent(this, productos::class.java)
            startActivity(intent)
        }
    }
}