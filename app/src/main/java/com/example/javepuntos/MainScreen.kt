package com.example.javepuntos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.javepuntos.databinding.ActivityMainScreenBinding
import com.example.javepuntos.model.Cliente
import com.example.javepuntos.model.TokenResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainScreen : Fragment() {

    private var _binding: ActivityMainScreenBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater:LayoutInflater, container: ViewGroup?,
        savedInstanceState:Bundle?
    ) :View? {

        _binding = ActivityMainScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gson = Gson()
        val tokenResponse:TokenResponse = gson.fromJson((activity as MainActivity).intent.getStringExtra("response_data"),object: TypeToken<TokenResponse>() {}.type)

        val token = tokenResponse.token
        val id = tokenResponse.id

        var nombre: TextView = binding.nombreUsuario
        var puntos: TextView = binding.SaldoUsuario

        llamarnombre(id,token){nombreAux ->
            if(nombreAux!=null.toString()){
                nombre.text = nombreAux
            }
        }

        llamarpuntos(id,token){puntosaux->
            if(puntosaux!=null){
                puntos.text = "${puntosaux} puntos."
            }
        }


        binding.buttonProductos.setOnClickListener {
            // Se lanza actividad nueva para los departamentos
            val intent = Intent(requireContext(), DepartamentosMain::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id)
            startActivity(intent)
        }
        binding.buttonCafeterias.setOnClickListener{
            // Se lanza actividad nueva para las cafeterias
            val intent = Intent(requireContext(), CafeteriasMain::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id)
            startActivity(intent)
        }
        binding.buttonPromocion.setOnClickListener{
//             Se lanza actividad nueva para las premios
            val intent = Intent(requireContext(), PromocionesActivity ::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id)
            startActivity(intent)
        }
//        binding.buttonPromocion.setOnClickListener{
//            // Se lanza actividad nueva para las premios
////            val intent = Intent(requireContext(), CafeteriasMain::class.java)
////            startActivity(intent)
//        }



    }

    fun llamarnombre(id_cliente: String, token:String, callback:(String)->Unit){
        val url = "${BASE_URL}/clientes/${id_cliente}"

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
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val responseData = response.body()?.string()
                    // Procesa la respuesta exitosa aquí
                    val clienteE: Cliente = gson.fromJson(responseData, object : TypeToken<Cliente>() {}.type)

                    callback(clienteE.nombrecliente)

                } else {
                    println("Error")
                    callback(null.toString())
                }
            }
        })
    }
    fun llamarpuntos(id_cliente: String, token: String, callback: (String) -> Unit){
        val url2 = "${BASE_URL}/clientes/${id_cliente}/balance"

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
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val responseData = response.body()?.string()
                    println(responseData)
                    // Procesa la respuesta exitosa aquí
                    val saldo: String = gson.fromJson(responseData, object : TypeToken<String>() {}.type)
                    callback(saldo)
                } else {
                    println("Error")
                    callback(null.toString())
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}