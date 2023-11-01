package com.example.javepuntos

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ActionMenuView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.javepuntos.databinding.ActivityHistorialBinding
import com.example.javepuntos.model.Tarjeta
import com.example.javepuntos.model.Transaccion
import com.example.javepuntos.model.cliente
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Historial : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialBinding
    private lateinit var listView: ListView
    private lateinit var adapter: TransaccionAdapter
    private val transacciones = ArrayList<Transaccion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("response_data")
        val id_cliente = intent.getStringExtra("id_cliente")
        var id_tarjeta: Int = 0
        listView = binding.listViewTransacciones
        adapter = TransaccionAdapter(this, transacciones)
        listView.adapter = adapter

        val url = "${BASE_URL}/clientes/${id_cliente}/tarjetas"
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
                    val tarjeta: ArrayList<Tarjeta> = gson.fromJson(responseData, object : TypeToken<ArrayList<Tarjeta>>() {}.type)
                    println(tarjeta)
                    id_tarjeta = tarjeta[0].idtarjeta

                    val url2 = "${BASE_URL}/tarjetas/${id_tarjeta}/tarjetascontpromociones"
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

                        override fun onResponse(call: Call, response: Response) {
                            if (response.isSuccessful) {
                                val gson = Gson()
                                val responseData2 = response.body()?.string()
                                println("Transaccion")
                                println(responseData2)
                                val transaccionList:ArrayList<Transaccion> = gson.fromJson(responseData2, object : TypeToken<ArrayList<Transaccion>>() {}.type)
                                transacciones.addAll(transaccionList)

                                // Actualiza el adaptador en el hilo principal
                                runOnUiThread {
                                    adapter.notifyDataSetChanged()
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
