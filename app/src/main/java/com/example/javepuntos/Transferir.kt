package com.example.javepuntos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.example.javepuntos.databinding.ActivityTransferirBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

class Transferir : AppCompatActivity() {

    private lateinit var binding: ActivityTransferirBinding
    private lateinit var id_cliente: String
    private lateinit var token: String

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        id_cliente = intent.getStringExtra("id_cliente").toString()
        token = intent.getStringExtra("response_data").toString()

        binding.buttonEP.setOnClickListener {
            val intent= Intent(this@Transferir,Transferir::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id_cliente)
            startActivity(intent)
        }

        binding.buttonHistorial.setOnClickListener {
            val intent= Intent(this@Transferir,Historial::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id_cliente)
            startActivity(intent)
        }

        binding.perfilPuntos.setOnClickListener {
            val intent = Intent(this@Transferir,EditarPerfilActivity::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("idCliente",id_cliente)
            startActivity(intent)
        }
        binding.botonMapa.setOnClickListener{
            val intent = Intent(this@Transferir, MapaCafeteriasActivity::class.java)
            // Iniciar la actividad
            startActivity(intent)
        }

        binding.ButtonEnviar.setOnClickListener {

            var nombreDestinatario: String = binding.nombreCampo.text.toString()
            var cedulaDestinatario: BigInteger = binding.CedulaCampo.text.toString().toBigInteger()
            var cantidad: Int = binding.CantidadCampo.text.toString().toInt()
            var descripcion: String = binding.DescripcionCampo.text.toString()
            var id_tarjeta_destino: Int = 0
            var fechaSF: Date = Date.from(Instant.now())

            // Definir el formato deseado (año-mes-día)
            val formato = SimpleDateFormat("yyyy-MM-dd HH:mm")

            // Formatear la fecha en el formato deseado
            val fecha = formato.format(fechaSF)
            var tarjeta_cliente: Int

            tarjetaDestino(cedulaDestinatario, token.toString()) { idTarjetaDestino ->
                if (idTarjetaDestino != -1) {
                    id_tarjeta_destino = idTarjetaDestino
                    println("Exito en tarjeta destino")
                    println(id_tarjeta_destino)

                    tarjetaOrigen(id_cliente, token.toString()) { idTarjetaOrigen ->
                        if (idTarjetaOrigen != -1) {
                            tarjeta_cliente = idTarjetaOrigen
                            println("Exito en tarjeta origen")
                            println(tarjeta_cliente)

                            envio(fecha,tarjeta_cliente, id_tarjeta_destino, descripcion, cantidad, token.toString()) { Renvio ->
                                if (Renvio) {
                                    println("Exito en envio")
                                    println(Renvio)
                                } else {
                                    Toast.makeText(applicationContext, "Error inesperado en el envio", Toast.LENGTH_SHORT).show()
                                    println("Error enviado")
                                    println(Renvio)
                                }
                            }
                        } else {
                            // Manejar el error de tarjeta de origen
                        }
                    }
                } else {
                    // Manejar el error de tarjeta de destino
                }
            }
        }

    }

    fun tarjetaOrigen(id_cliente:String,token: String, callback: (Int) -> Unit){
        try{
            val url = "${BASE_URL}/clientes/${id_cliente}/tarjetas/id_cliente"
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
                    callback(-1)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val gson = Gson()
                        val responseData = response.body()?.string()
                        val id: Int = gson.fromJson(responseData, object : TypeToken<Int>() {}.type)
                        callback(id)



                    } else {
                        println("Error")
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener la tarjeta origen",
                            Toast.LENGTH_SHORT
                        ).show()
                        callback(-1)
                    }
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    "Error inesperado: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            callback(-1)
        }
    }

    fun tarjetaDestino(cedula:BigInteger, token:String, callback: (Int) -> Unit) {
        try{
            val url = "${BASE_URL}/clientes/cedula/${cedula}"
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
                    println("Error en la solicitud")
                    callback(-1)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val gson = Gson()
                        val responseData = response.body()?.string()
                        val id_tarjeta: Int = gson.fromJson(responseData, object : TypeToken<Int>() {}.type)

                        callback(id_tarjeta)

                    } else {
                        println("Error")
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Error al obtener la tarjeta destino", Toast.LENGTH_SHORT).show()
                        }
                        println("Error en la respuesta")
                        println(response)
                        callback(-1)
                    }
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(applicationContext,"Error inesperado: ${e.message}",Toast.LENGTH_SHORT).show()
            callback(-1)
        }

    }

    fun envio(
        fecha: String,
        origen:Int,
        destino:Int,
        descipcion:String,
        cantidad:Int,
        token:String,
        callback: (Boolean) -> Unit){
        try{
            println("Entro")
            val url = "${BASE_URL}/tarjetas/enviar?Origen=${origen}&Destino=${destino}&cantidad=${cantidad}&Description=${descipcion}&fecha=${fecha}"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer $token")
                .post(RequestBody.create(null,ByteArray(0)))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error en la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    callback(false)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        callback(true)

                    } else {
                        println("Error")
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Error al enviar los puntos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        callback(false)
                    }
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    "Error inesperado: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            callback(false)
        }
    }
}