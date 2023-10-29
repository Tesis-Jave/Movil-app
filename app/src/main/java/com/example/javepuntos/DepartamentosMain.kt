package com.example.javepuntos

import DepartamentoAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.model.Departamento
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class DepartamentosMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamentos_main)

        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        // Recuperar el token para usaro en las consultas
        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "http://$BASE_URL/departamentos/"

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
                            println(i)
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

