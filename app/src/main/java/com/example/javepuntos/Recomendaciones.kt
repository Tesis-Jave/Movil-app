package com.example.javepuntos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.javepuntos.databinding.ActivityRecomendacionesBinding
import com.example.javepuntos.model.Articulos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Recomendaciones : AppCompatActivity() {

    private lateinit var articulosAdapter: ArticulosAdapter
    private lateinit var listView: ListView
    private lateinit var binding: ActivityRecomendacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token= intent.getStringExtra("response_data")
        val id_cliente = intent.getStringExtra("id_cliente")

        binding.buttonEP.setOnClickListener {
            val intent=Intent(this@Recomendaciones,Transferir::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id_cliente)
            startActivity(intent)
        }

        binding.buttonHistorial.setOnClickListener {
            val intent=Intent(this@Recomendaciones,Historial::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id_cliente)
            startActivity(intent)
        }

        binding.perfilPuntos.setOnClickListener {
            val intent = Intent(this@Recomendaciones,EditarPerfilActivity::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("idCliente",id_cliente)
            startActivity(intent)
        }
        binding.botonMapa.setOnClickListener{
            val intent = Intent(this, MapaCafeteriasActivity::class.java)
            // Iniciar la actividad
            startActivity(intent)
        }

        val url = "$BASE_URL/tarjetascontpromociones/recomendaciones/${id_cliente}"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    println("FALLO CON EL ENDPOINT1213")
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
                        val lista: List<Articulos> = gson.fromJson(
                            responseData,
                            object : TypeToken<List<Articulos>>() {}.type
                        )

                        listView = binding.listViewArticulos
                        articulosAdapter = ArticulosAdapter(this@Recomendaciones, lista)
                        listView.adapter = articulosAdapter

                    }
                } else {
                    // Manejar respuestas de error
                    println("Error en la peticion")
                    println(response.message())
                }
            }
        })


    }
}