package com.example.javepuntos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.javepuntos.databinding.ActivityTransferirBinding
import com.example.javepuntos.model.Tarjeta
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.math.BigInteger
import java.time.Instant
import java.util.Date

class Transferir : AppCompatActivity() {

    private lateinit var binding: ActivityTransferirBinding

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id_cliente = intent.getStringExtra("id_cliente")
        val token = intent.getStringExtra("response_data")


        var nombreDestinatario: String = binding.nombreCampo.text.toString()
        var cedulaDestinatario: BigInteger = binding.CedulaCampo.text.toString().toBigInteger()
        var cantidad: Int = binding.CantidadCampo.text.toString().toInt()
        var descripcion: String = binding.DescripcionCampo.text.toString()
        var id_tarjeta_destino:Int=0
        var fecha: Date= Date.from(Instant.now())

        tarjetaDestino(cedulaDestinatario, token.toString()) { idTarjetaDestino ->
            // Lógica para manejar idTarjetaDestino aquí
            if (idTarjetaDestino != -1) {
                // La llamada a la función fue exitosa, puedes usar idTarjetaDestino aquí
                id_tarjeta_destino = idTarjetaDestino
            } else {
                // Manejar el error, por ejemplo, mostrar un mensaje de error
                runOnUiThread {
                    Toast.makeText(applicationContext, "El cliente al que desea enviar puntos no existe en el sistema", Toast.LENGTH_SHORT).show()
                }
            }
        }

        var tarjeta_cliente: Int

        if (id_cliente != null) {
            tarjetaOrigen(id_cliente, token.toString()) { idTarjetaOrigen ->
                // Lógica para manejar idTarjetaDestino aquí
                if (idTarjetaOrigen != -1) {
                    // La llamada a la función fue exitosa, puedes usar idTarjetaDestino aquí
                    tarjeta_cliente = idTarjetaOrigen
                } else {
                    // Manejar el error, por ejemplo, mostrar un mensaje de error
                    runOnUiThread {
                        Toast.makeText(applicationContext, "El cliente al que desea enviar puntos no existe en el sistema", Toast.LENGTH_SHORT).show()
                    }
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
                            "Error al obtener el cliente",
                            Toast.LENGTH_SHORT
                        ).show()
                        callback(-1)
                    }
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(applicationContext,"Error inesperado: ${e.message}", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener el cliente",
                            Toast.LENGTH_SHORT
                        ).show()
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
}