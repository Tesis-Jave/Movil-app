package com.example.javepuntos

import DepartamentoAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import com.example.javepuntos.model.Departamento
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class DepartamentosMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamentos_main)

        // Recuperar el token para usaro en las consultas
        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)
        val url = "http://192.168.1.5:3000/departamentos" //German
        //val url = "http://192.168.56.1:3000/perfils/" // Juan M

        // Realizar la solicitud para obtener la lista de departamentos
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejar errores de conexión
                runOnUiThread {
                    println("FALLO CON EL ENDPOINT")
                    Toast.makeText(applicationContext, "Error al obtener departamentos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // Procesar la respuesta del servidor
                runOnUiThread {

                    println("entra a onresponse")
                    if (response.isSuccessful) {
                        println("entra a if de respuesta")
                        val responseData = response.body()?.string()

                        // Convertir el JSON en una lista de objetos Departamento
                        val gson = Gson()
                        val departamentos: List<Departamento> = gson.fromJson(
                            responseData,
                            object : TypeToken<List<Departamento>>() {}.type
                        )

                        // Crear y configurar el adaptador
                        val adapter = DepartamentoAdapter(this@DepartamentosMain, departamentos)

                        // Obtener el GridLayout y establecer el adaptador
                        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
                        for (i in 0 until adapter.count) {
                            val view = adapter.getView(i, null, gridLayout)
                            gridLayout.addView(view)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Error al obtener departamentos",
                                Toast.LENGTH_SHORT
                            ).show()
                            println("fallo al cargar los datos")
                        }
                    }
                }
            }
        })
    }
}



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
