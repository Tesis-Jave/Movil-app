package com.example.javepuntos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.javepuntos.databinding.SignupBinding
import com.example.javepuntos.model.TokenResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.Calendar

class SignUpFragment : Fragment() {
    private var _binding: SignupBinding?=null
    private val binding get() = _binding!!

    private lateinit var birthDateCampo: EditText

    public fun volver(){
        findNavController().navigate(R.id.volver)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SignupBinding.inflate(inflater, container, false)
        birthDateCampo = binding.birthDateCampo
        birthDateCampo.setOnClickListener {
            showDatePickerDialog()
        }

        binding.volver.setOnClickListener {
            findNavController().navigate(R.id.volver)
        }
        binding.buttonCrearCuenta.setOnClickListener {

            val urlC = "$BASE_URL/clientes"
            val jsonC = JSONObject()
            jsonC.put("nombrecliente",binding.nameCampo.text.toString())
            jsonC.put("nombrecomercial",binding.nameCampo.text.toString())
            jsonC.put("cif",binding.cedulaCampo.text.toString().toBigInteger())
            jsonC.put("direccion1",binding.addressCampo.text.toString())
            jsonC.put("telefono1",binding.phoneCampo.text.toString().toBigInteger())
            jsonC.put("e_mail",binding.emailCampo.text.toString())
            jsonC.put("tipocliente",1)
            println(jsonC.toString())

            val clientC = OkHttpClient()

            val requestBodyC = RequestBody.create(MediaType.parse("application/json"),jsonC.toString())
            val requestC = Request.Builder()
                .url(urlC)
                .post(requestBodyC)
                .build()

            clientC.newCall(requestC).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Manejo de errores en caso de que la solicitud falle
                    println(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val url = "$BASE_URL/perfils"
                        val json = JSONObject()
                        json.put("usuario", binding.usernameCampo.text.toString())
                        json.put("password", binding.passwordCampo.text.toString())
                        json.put("nombre", binding.nameCampo.text.toString())
                        json.put("cedula", binding.cedulaCampo.text.toString().toInt())
                        json.put("cargo", "Cliente")
                        json.put("admin", false)

                        val client = OkHttpClient()

                        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

                        val request = Request.Builder()
                            .url(url)
                            .post(requestBody)
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
                    } else {
                        println("Error")
                        println("Error al crear el cliente")
                        println(response)
                    }
                }
            })



        }

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun mostrarAlerta(mensaje: String) {
        Handler(Looper.getMainLooper()).post {
            binding.usernameCampo.text.clear()
            binding.passwordCampo.text.clear()
            binding.nameCampo.text.clear()
            binding.cedulaCampo.text.clear()
            binding.birthDateCampo.text.clear()
            binding.addressCampo.text.clear()
            binding.emailCampo.text.clear()
            binding.phoneCampo.text.clear()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Alerta")
            builder.setMessage(mensaje)
            builder.setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Aquí puedes manejar la fecha seleccionada, por ejemplo, actualizar el texto en el EditText.
                val selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
                birthDateCampo.setText(selectedDate)
            },
            // Establece la fecha actual como valor predeterminado al abrir el selector de fecha.
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )

        // Muestra el diálogo de selección de fecha.
        datePickerDialog.show()
    }



}