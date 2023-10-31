package com.example.javepuntos

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.model.Articulos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ArticulosActivity : AppCompatActivity() {

    private lateinit var articulosAdapter: ArticulosAdapter
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articulo) // Asegúrate de que estás inflando el layout correcto aquí

        listView = findViewById(R.id.listViewArticulos)
        articulosAdapter = ArticulosAdapter(this, ArrayList())
        listView.adapter = articulosAdapter

        val idDepartamento = intent.getIntExtra("idDepartamento", -1)
        obtenerArticulosPorDepartamento(idDepartamento)
    }

    private fun obtenerArticulosPorDepartamento(idDepartamento: Int) {
        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "http://$BASE_URL/articulos-por-departamento/$idDepartamento"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    println("FALLO CON EL ENDPOINT")
                    Toast.makeText(applicationContext, "Error al obtener artículos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val responseData = response.body()?.string()

                    runOnUiThread {
                        // Parsear el JSON y actualizar la interfaz de usuario
                        val gson = Gson()
                        val articulos: List<Articulos> = gson.fromJson(
                            responseData,
                            object : TypeToken<List<Articulos>>() {}.type
                        )
                        println(articulos)

                        // Crear el adaptador con los nuevos datos
                        articulosAdapter = ArticulosAdapter(this@ArticulosActivity, articulos)

                        // Establecer el adaptador en el ListView existente
                        listView.adapter = articulosAdapter

                        // Por ejemplo, actualizar el adaptador de la lista con los nuevos datos
                    }
                } else {
                    // Manejar respuestas de error
                }
            }
        })
    }
}