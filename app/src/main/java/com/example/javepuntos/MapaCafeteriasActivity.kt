package com.example.javepuntos

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.databinding.ActivityMapaCafeteriasBinding

class MapaCafeteriasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapaCafeteriasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaCafeteriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Forzar la orientación horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)

        // Mostrar el botón de retroceso en el Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Cargar imagen en ImageView
        binding.mapaImageView.setImageResource(R.drawable.mapacafeterias)
    }

    // Manejar el evento de hacer clic en el botón de retroceso en el Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}