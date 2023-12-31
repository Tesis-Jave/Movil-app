package com.example.javepuntos

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.databinding.ActivityPerfilPuntosBinding
import com.example.javepuntos.model.Cliente
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

        val token1 = intent.getStringExtra("response_data")

        val idCliente = intent.getStringExtra("idCliente")

        binding = ActivityPerfilPuntosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEP.setOnClickListener {
            val intent= Intent(this@Perfil_Puntos,Transferir::class.java)
            intent.putExtra("response_data",token1)
            intent.putExtra("id_cliente",idCliente)
            startActivity(intent)
        }

        binding.buttonHistorial.setOnClickListener {
            val intent= Intent(this@Perfil_Puntos,Historial::class.java)
            intent.putExtra("response_data",token1)
            intent.putExtra("id_cliente",idCliente)
            startActivity(intent)
        }

        binding.perfilPuntos.setOnClickListener {
            val intent = Intent(this@Perfil_Puntos,EditarPerfilActivity::class.java)
            intent.putExtra("response_data",token1)
            intent.putExtra("idCliente",idCliente)
            startActivity(intent)
        }
        binding.botonMapa.setOnClickListener{
            val intent = Intent(this@Perfil_Puntos, MapaCafeteriasActivity::class.java)
            // Iniciar la actividad
            startActivity(intent)
        }

        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }


        if (idCliente==null){
            finish()
        }

        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "${BASE_URL}/clientes/${idCliente}"
        println(url)

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
                    val gson = Gson()
                    val responseData = response.body()?.string()
                    println(responseData)
                    // Procesa la respuesta exitosa aquí
                    val clienteE: Cliente = gson.fromJson(responseData, object : TypeToken<Cliente>() {}.type)

                    if (clienteE != null) {
                        runOnUiThread {
                            binding.nombreTextView.text = clienteE.nombrecliente

                        }
                    } else {
                        println("No se encontraron clientes en la respuesta JSON")
                    }

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

        val url2 = "${BASE_URL}/clientes/${idCliente}/balance"

        // Realizar la solicitud para obtener la lista de departamentos
        val client2 = OkHttpClient()
        val request2 = Request.Builder()
            .url(url2)
            .header("Authorization", "Bearer $token")
            .build()

        client2.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejo de errores en caso de que la solicitud falle
                println(e)
                finish()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val responseData = response.body()?.string()
                    println(responseData)
                    // Procesa la respuesta exitosa aquí
                    val saldo: String = gson.fromJson(responseData, object : TypeToken<String>() {}.type)

                    runOnUiThread {
                        binding.saldoTextView.text ="$saldo puntos"

                    }

                } else {
                    println("Error")
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener el saldo del cliente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        binding.transferir.setOnClickListener {

            intent = Intent(this,Transferir::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",idCliente)
            startActivity(intent)

        }

        binding.historial.setOnClickListener {

            intent = Intent(this,Historial::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",idCliente)
            startActivity(intent)

        }

    }


}