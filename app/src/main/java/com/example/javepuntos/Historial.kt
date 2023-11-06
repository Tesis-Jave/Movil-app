package com.example.javepuntos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.databinding.ActivityHistorialBinding
import com.example.javepuntos.model.Tarjeta
import com.example.javepuntos.model.Transaccion
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

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        val token = intent.getStringExtra("response_data")
        val id_cliente = intent.getStringExtra("id_cliente")

        binding.buttonEP.setOnClickListener {
            val intent= Intent(this@Historial,Transferir::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id_cliente)
            startActivity(intent)
        }

        binding.buttonHistorial.setOnClickListener {
            val intent= Intent(this@Historial,Historial::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id_cliente)
            startActivity(intent)
        }

        binding.perfilPuntos.setOnClickListener {
            val intent = Intent(this@Historial,EditarPerfilActivity::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("idCliente",id_cliente)
            startActivity(intent)
        }
        binding.botonMapa.setOnClickListener{
            val intent = Intent(this@Historial, MapaCafeteriasActivity::class.java)
            // Iniciar la actividad
            startActivity(intent)
        }

        var id_tarjeta: Int = 0
        listView = binding.listViewTransacciones
        adapter = TransaccionAdapter(this, transacciones)
        listView.adapter = adapter
        try{
            val url = "${BASE_URL}/clientes/${id_cliente}/tarjetas"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error en la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
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
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(applicationContext,"Error inesperado: ${e.message}", Toast.LENGTH_SHORT).show()
        }


    }
}
