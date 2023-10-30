package com.example.javepuntos

import android.content.Context
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.model.Cafeteria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CafeteriasMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeterias_main)

        // Recuperar el token para usaro en las consultas
        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "http://$BASE_URL/cafeterias/"

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
                    Toast.makeText(applicationContext, "Error al obtener cafeterias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {

            println("entra a onresponse Cafeteria")
            // Procesar la respuesta del servidor
                runOnUiThread {
                    if (response.isSuccessful) {
                        println("Response satisfactoria")
                        val responseData = response.body()?.string()

                        val gson = Gson()
                        val cafeterias: List<Cafeteria> = gson.fromJson(
                            responseData,
                            object : TypeToken<List<Cafeteria>>() {}.type
                        )
                        println(cafeterias)

                        val adapter = CafeteriasAdapter(this@CafeteriasMain, cafeterias)

                        val gridLayout: GridLayout = findViewById(R.id.gridLayout)

                            for (i in 0 until adapter.count){
                                val view = adapter.getView(i, null, gridLayout)
                                gridLayout.addView(view)
                            }


                    }else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Error al obtener Cafeterias",
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

