package com.example. javepuntos

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.databinding.ActivityEditarPerfilBinding
import com.example.javepuntos.model.Cliente
import com.example.javepuntos.model.Perfil
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

class EditarPerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarPerfilBinding
    private var perfil: Perfil? = null
    private var cliente: Cliente? = null
    private var perfilRecibido = false
    private var clienteRecibido = false

    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idCliente = intent.getStringExtra("idCliente")
        var idperfil = 0
        val token = intent.getStringExtra("response_data")

        val tipoCliente = object : TypeToken<Cliente>() {}.type
        val tipoPerfil = object : TypeToken<Perfil>() {}.type

        val url = "$BASE_URL/clientes/perfil/${idCliente}"
        println(url)
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
                    // Procesa la respuesta exitosa aquí
                    val responseBody = response.body()?.string()



                    val jsonObject = JSONObject(responseBody) // Convierte la respuesta en un objeto JSON

                    val gson = Gson()

                    val clienteJson = jsonObject.getJSONObject("cliente").toString()
                    val perfilJson = jsonObject.getJSONObject("perfil").toString()

                    val cliente: Cliente = gson.fromJson(clienteJson, tipoCliente)
                    val perfil: Perfil = gson.fromJson(perfilJson, tipoPerfil)

                    idperfil = perfil.id_perfil

                    println(cliente.toString())
                    println(perfil.toString())

                    runOnUiThread {
                        binding.editTextCif.setText(cliente.cif.toString())
                        binding.editTextDireccion.setText(cliente.direccion1)
                        binding.editTextEmail.setText(cliente.e_mail)
                        binding.editTextNombreCliente.setText(cliente.nombrecliente)
                        binding.editTextFechaNacimiento.setText(cliente.fechanacimiento.toString())
                        binding.editTextPassword.setText(perfil.password)
                        binding.editTextUsuario.setText(perfil.usuario)
                        binding.editTextTelefono.setText(cliente.telefono1.toString())
                    }

                } else {
                    println("Error")
                    println(response.message())
                }
            }
        })

        binding.buttonGuardar.setOnClickListener {
            if (token != null && binding.editTextPassword.text.toString()==binding.editTextPasswordConfirm.text.toString()) {
                println("Entro0")
                if(idCliente!=null){
                    println("Entro1")
                    modificar(idCliente, idperfil,token)
                }

            }else{
                mostrarAlerta("No coincide la contraseña con la confirmación de la contraseña")
            }
        }

    }

    private fun mostrarAlerta(mensaje: String) {
        Handler(Looper.getMainLooper()).post {
            binding.editTextPassword.text.clear()
            binding.editTextPasswordConfirm.text.clear()
            val builder = AlertDialog.Builder(this@EditarPerfilActivity)
            builder.setTitle("Alerta")
            builder.setMessage(mensaje)
            builder.setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    fun modificar(id_cliente: String, id_perfil:Int, token:String){
        println("entro2")

        var clienteM = Cliente(
            nombrecliente = binding.editTextNombreCliente.text.toString(),
            direccion1 = binding.editTextDireccion.text.toString(),
            e_mail = binding.editTextEmail.text.toString(),
            fechanacimiento = binding.editTextFechaNacimiento.text.toString(),
            telefono1 = binding.editTextTelefono.text.toString().toLong(),
            cif = binding.editTextCif.text.toString().toLong(),
            // Establece el resto de las propiedades a null
            nombrecomercial = null,
            alias = null,
            fax = null,
            faxpedidos = null,
            digcontrolblanco = null,
            codpostalbanco = null,
            nombrebanco = null,
            poblacionbanco = null,
            sexo = null,
            nif20 = null,
            descatalogado = null,
            tipocliente = null,
            id_cliente = id_cliente.toInt()
        )

        var perfilM = Perfil(
            usuario = binding.editTextUsuario.text.toString(),
            password = binding.editTextPassword.text.toString(),
            id_perfil = id_perfil,
            nombre = binding.editTextNombreCliente.text.toString(),
            cedula = binding.editTextCif.text.toString().toLong(),
            cargo = "Cliente",
            admin = false
        )
        val clienteJSON = JSONObject()
        clienteJSON.put("nombrecliente", clienteM.nombrecliente)
        clienteJSON.put("nombrecomercial", clienteM.nombrecomercial)
        clienteJSON.put("cif", clienteM.cif)
        clienteJSON.put("alias", clienteM.alias)
        clienteJSON.put("direccion1", clienteM.direccion1)
        clienteJSON.put("telefono1", clienteM.telefono1)
        clienteJSON.put("fax", clienteM.fax)
        clienteJSON.put("faxpedidos", clienteM.faxpedidos)
        clienteJSON.put("e_mail", clienteM.e_mail)
        clienteJSON.put("digcontrolblanco", clienteM.digcontrolblanco)
        clienteJSON.put("codpostalbanco", clienteM.codpostalbanco)
        clienteJSON.put("nombrebanco", clienteM.nombrebanco)
        clienteJSON.put("poblacionbanco", clienteM.poblacionbanco)
        clienteJSON.put("fechanacimiento", clienteM.fechanacimiento)
        clienteJSON.put("sexo", clienteM.sexo)
        clienteJSON.put("nif20", clienteM.nif20)
        clienteJSON.put("descatalogado", clienteM.descatalogado)
        clienteJSON.put("tipocliente", clienteM.tipocliente)
        clienteJSON.put("id_cliente", clienteM.id_cliente)

        val perfilJSON = JSONObject()
        perfilJSON.put("id_perfil", perfilM.id_perfil)
        perfilJSON.put("usuario", perfilM.usuario)
        perfilJSON.put("password", perfilM.password)
        perfilJSON.put("nombre", perfilM.nombre)
        perfilJSON.put("cedula", perfilM.cedula)
        perfilJSON.put("cargo", perfilM.cargo)
        perfilJSON.put("admin", perfilM.admin)

        val json = JSONObject()
        json.put("clientes", clienteJSON)
        json.put("perfil", perfilJSON)

        val url = "$BASE_URL/clientes/${id_cliente}"

        println(json)

        val client = OkHttpClient()

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .header("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Modificacion exitosa")
                    finish()
                } else {
                    println("Error")
                    println(response.message())
                }
            }
        })
    }

}
