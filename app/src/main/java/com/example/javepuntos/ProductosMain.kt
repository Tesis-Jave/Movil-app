package com.example.javepuntos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.javepuntos.model.Articulo
import com.squareup.picasso.Picasso

class ProductosMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_main)


//        val gridLayout = findViewById<GridLayout>(R.id.gridLayout) // Obtén la referencia al GridLayout desde el XML
//        val listaDeArticulos: List<Articulo> = obtenerListaDeArticulos() // Obtén tu lista de objetos Articulo
//
//        for (articulo in listaDeArticulos) {
//            val imageButton = ImageButton(this)
//            imageButton.layoutParams = GridLayout.LayoutParams() // Crea parámetros de diseño para el botón
//            imageButton.layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT
//            imageButton.layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
//
//            // Carga la imagen desde la URL (usando una biblioteca como Picasso o Glide) y colócala en el ImageButton
//            Picasso.get().load(articulo.foto).into(imageButton)
//
//            val textView = TextView(this)
//            textView.layoutParams = GridLayout.LayoutParams() // Crea parámetros de diseño para el TextView
//            textView.layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT
//            textView.layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
//            textView.text = articulo.descripcion // Establece el nombre del artículo en el TextView
//            textView.gravity = Gravity.CENTER // Alinea el texto al centro
//
//            // Agrega el ImageButton y el TextView al GridLayout
//            gridLayout.addView(imageButton)
//            gridLayout.addView(textView)
//        }

    }
}