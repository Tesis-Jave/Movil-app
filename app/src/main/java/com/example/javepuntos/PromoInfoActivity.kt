package com.example.javepuntos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.javepuntos.model.Articulos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class PromoInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_info)

        // back button
        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        val intent = intent

        val promoId = intent.getIntExtra("PROMO_ID", -1)
        println("Este es el id de la promo: $promoId")

        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val context = this@PromoInfoActivity

        val url = "$BASE_URL/promociones/$promoId/articulos"

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
                println("entra a onresponse")
                runOnUiThread {
                    if (response.isSuccessful) {
                        println("entra a if de respuesta")
                        val responseData = response.body()?.string()

                        // Convertir el JSON en una lista de objetos Departamento
                        val gson = Gson()
                        val articulos: List<Articulos> = gson.fromJson(
                            responseData,
                            object : TypeToken<List<Articulos>>() {}.type
                        )
                        println(articulos)
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                        val layoutManager = LinearLayoutManager(context)
                        recyclerView.layoutManager = layoutManager
                        val adapter = PromoInfoAdapter(this@PromoInfoActivity, articulos)
//                        recyclerView.adapter = adapter
                        recyclerView.adapter = adapter


                    }
//                    } else {
//                        Toast.makeText(
//                            applicationContext,
//                            "Error al obtener departamentos",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        println("fallo al cargar los datos")
//                    }
                }
            }
        })




    }
}