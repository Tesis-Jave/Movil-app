package com.example.javepuntos

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.databinding.ActivityPerfilPuntosBinding

class Perfil_Puntos : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilPuntosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilPuntosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        val token = intent.getStringExtra("response_data")

    }


}