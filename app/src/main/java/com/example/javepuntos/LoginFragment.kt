package com.example.javepuntos

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.javepuntos.databinding.LoginBinding
import com.example.javepuntos.model.TokenResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

import java.io.IOException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: LoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.SignUpAction)
        }

        binding.buttonLogin.setOnClickListener {
            // val url = "http://192.168.1.35:3000/perfils/login?usuario=${binding.user.text.toString()}&password=${binding.password.text.toString()}" // Germán
            val url = "http://192.168.56.1:3000/perfils/login?usuario=${binding.user.text.toString()}&password=${binding.password.text.toString()}" // Juan M
            println(url)
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .post(RequestBody.create(null, ByteArray(0)))
                .build()


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Manejo de errores en caso de que la solicitud falle
                    println(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        // Procesa la respuesta exitosa aquí
                        val responseData = response.body()?.string()
                        // Almacena el token en SharedPreferences
                        val gson = Gson()
                        val tokenResponse: TokenResponse = gson.fromJson(responseData, object : TypeToken<TokenResponse>() {}.type)

                        val sharedPreferences = requireActivity().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("TOKEN_KEY", tokenResponse.token)
                        editor.apply()


                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("response_data", responseData)
                        startActivity(intent)
                        // Puedes mostrar la respuesta en tu interfaz de usuario
                    } else {
                        println("Error")

                        mostrarAlerta("Credenciales incorrectas")
                        // Manejo de errores en caso de que la respuesta no sea exitosa
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun mostrarAlerta(mensaje: String) {
        Handler(Looper.getMainLooper()).post {
            binding.user.text.clear()
            binding.password.text.clear()
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Alerta")
            builder.setMessage(mensaje)
            builder.setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

}