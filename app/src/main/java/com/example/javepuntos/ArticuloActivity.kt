package com.example.javepuntos

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.databinding.ActivityArticuloBinding
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

    private lateinit var binding: ActivityArticuloBinding


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
         // Asegúrate de que estás inflando el layout correcto aquí

        binding = ActivityArticuloBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idDepartamento = intent.getIntExtra("idDepartamento", -1)


        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "$BASE_URL/articulos-por-departamento/$idDepartamento"

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
                        articulosAdapter = ArticulosAdapter(this@ArticulosActivity, lista)
                        listView.adapter = articulosAdapter

                        println(lista)
                        // Por ejemplo, actualizar el adaptador de la lista con los nuevos datos
                    }
                } else {
                    // Manejar respuestas de error
                }
            }
        })




    }
}