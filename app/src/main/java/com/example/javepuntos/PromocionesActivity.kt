package com.example.javepuntos

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.model.Promociones
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class PromocionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promociones)
        // boton de volver
        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }
        // recuperar token
        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", "") ?: ""

        val url = "$BASE_URL/promociones/"

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
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener promociones",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                // Manejar la respuesta aquí
                runOnUiThread {
                    if (response.isSuccessful) {
                        val responseData = response.body()?.string()

                        // Convertir el JSON en una lista de objetos Promociones
                        val gson = Gson()
                        val promociones: List<Promociones> = gson.fromJson(
                            responseData,
                            object : TypeToken<List<Promociones>>() {}.type
                        )

                        // Crear adaptador
                        val adapter = PromoAdapter(applicationContext, promociones, token)

                        // Obtener la referencia del ListView de tu XML
                        val listView: ListView = findViewById(R.id.listViewPromociones)

                        // Establecer el adaptador en el ListView
                        listView.adapter = adapter

                        // Implementar el clic del elemento de la lista si es necesario
                        listView.setOnItemClickListener { parent, view, position, id ->
                            // Manejar el clic del elemento de la lista aquí
                            val selectedPromocion = promociones[position]
                            Toast.makeText(
                                applicationContext,
                                "Promoción seleccionada: ${selectedPromocion.descripcion}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }
}