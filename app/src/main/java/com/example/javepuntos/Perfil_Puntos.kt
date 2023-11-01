package com.example.javepuntos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.databinding.ActivityPerfilPuntosBinding
import com.example.javepuntos.model.TokenResponse
import com.example.javepuntos.model.cliente
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Perfil_Puntos : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilPuntosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilPuntosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        val gson =Gson()
        val tokenResponse: TokenResponse = gson.fromJson(intent.getStringExtra("response_data"), object : TypeToken<TokenResponse>() {}.type)

        val idCliente = tokenResponse.id
        println("Id de cliente")
        println(idCliente)

        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "$BASE_URL/clientes?id=$idCliente"

        // Realizar la solicitud para obtener la lista de departamentos
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejo de errores en caso de que la solicitud falle
                println(e)
                finish()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.string()
                    // Procesa la respuesta exitosa aquí
                    val cliente : cliente = gson.fromJson(responseData,object : TypeToken<cliente>(){}.type)
                    binding.nombreTextView.setText(cliente.nombrecliente)
                } else {
                    println("Error")
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener el cliente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }


}