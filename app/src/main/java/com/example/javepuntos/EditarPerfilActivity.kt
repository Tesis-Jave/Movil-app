package com.example.javepuntos

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.databinding.ActivityEditarPerfilBinding
import com.example.javepuntos.model.Cliente
import com.example.javepuntos.model.Perfil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
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
        val idCliente = intent.getIntExtra("idCliente", -1)

    }

    override fun onResume() {
        super.onResume()
        val idPerfil = intent.getIntExtra("idPerfil", -1)
        val idCliente = intent.getIntExtra("idCliente", -1)

        val urlPerfil = "$BASE_URL/perfils/$idPerfil"
        val urlCliente = "$BASE_URL/clientes/$idCliente"

        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val requestPerfil = Request.Builder()
            .url(urlPerfil)
            .header("Authorization", "Bearer $token")
            .build()

        val requestCliente = Request.Builder()
            .url(urlCliente)
            .header("Authorization", "Bearer $token")
            .build()

        // Hacer la solicitud para el perfil
        client.newCall(requestPerfil).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    println("Fallo con el endpoint de perfil")
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener el perfil",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                if (response.isSuccessful) {
                    val json = JSONObject(responseData)
                    when (call.request().url().toString()) {
                        // Manejar la respuesta del perfil
                        "$BASE_URL/perfils/$idPerfil" -> {
                            val idPerfil = json.getInt("id_perfil")
                            val usuario = json.getString("usuario")
                            val password = json.getString("password")
                            val nombre = json.getString("nombre")
                            val cedula = json.getLong("cedula")
                            val cargo = json.getString("cargo")
                            val admin = json.getBoolean("admin")
                            perfil = Perfil(idPerfil,
                                            usuario,
                                password,
                                nombre,
                                cedula,
                                cargo,
                                admin
                            )
                            perfilRecibido = true
                        }
                    }

                    // Actualizar los campos si tanto el perfil como el cliente han sido recibidos
                    if (perfilRecibido && clienteRecibido) {
                        runOnUiThread {
                            actualizarCampos()
                        }
                    }
                } else {
                    // Manejar errores para la solicitud del perfil
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener datos del servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        // Hacer la solicitud para el cliente
        client.newCall(requestCliente).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    println("Fallo con el endpoint de Cliente")
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener el Cliente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // Procesar la respuesta para la solicitud del cliente
                if (response.isSuccessful) {
                    val responseData = response.body()?.string()
                    val json = JSONObject(responseData)

                    // Manejar la respuesta del cliente
                    when (call.request().url().toString()) {
                        "$BASE_URL/clientes/$idCliente" -> {
                            val idCliente = json.getInt("id_cliente")
                            val nombreCliente = json.getString("nombrecliente")
                            val nombreComercial = json.getString("nombrecomercial")
                            val cif = json.getLong("cif")
                            val alias = json.getString("alias")
                            val direccion1 = json.getString("direccion1")
                            val telefono1 = json.getLong("telefono1")
                            val fax = json.getString("fax")
                            val faxPedidos = json.getString("faxpedidos")
                            val email = json.getString("e_mail")
                            val digControlBlanco = json.getString("digcontrolblanco")
                            val codPostalBanco = json.getString("codpostalbanco")
                            val nombreBanco = json.getString("nombrebanco")
                            val poblacionBanco = json.getString("poblacionbanco")
                            val fechaNacimiento = json.getString("fechanacimiento")
                            val sexo = json.getString("sexo")
                            val nif20 = json.getString("nif20")
                            val descatalogado = json.getInt("descatalogado")
                            val tipoCliente = json.getInt("tipocliente")
                            cliente = Cliente(
                                idCliente,
                                nombreCliente,
                                nombreComercial,
                                cif,
                                alias,
                                direccion1,
                                telefono1,
                                fax,
                                faxPedidos,
                                email,
                                digControlBlanco,
                                codPostalBanco,
                                nombreBanco,
                                poblacionBanco,
                                fechaNacimiento,
                                sexo,
                                nif20,
                                descatalogado,
                                tipoCliente
                            )
                            clienteRecibido = true
                        }
                    }

                    // Actualizar los campos si tanto el perfil como el cliente han sido recibidos
                    if (perfilRecibido && clienteRecibido) {
                        runOnUiThread {
                            actualizarCampos()
                        }
                    }
                } else {
                    // Manejar errores para la solicitud del cliente
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener datos del servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun actualizarCampos() {
        val editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        // Cliente
        val editTextNombreCliente = findViewById<EditText>(R.id.editTextNombreCliente)
        val editTextCif = findViewById<EditText>(R.id.editTextCif)
        val editTextAlias = findViewById<EditText>(R.id.editTextAlias)
        val editTextDireccion = findViewById<EditText>(R.id.editTextDireccion)
        val editTextTelefono = findViewById<EditText>(R.id.editTextTelefono)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextFechaNacimiento = findViewById<EditText>(R.id.editTextFechaNacimiento)
        // Perfil

        // Cliente
        editTextUsuario.setText(perfil?.usuario ?: "")
        editTextNombreCliente.setText(cliente?.nombrecliente ?: "")
        editTextCif.setText(cliente?.cif.toString() ?: "")
        editTextAlias.setText(cliente?.alias ?: "")
        editTextDireccion.setText(cliente?.direccion1 ?: "")
        editTextTelefono.setText(cliente?.telefono1.toString() ?: "")
        editTextEmail.setText(cliente?.e_mail ?: "")
        editTextFechaNacimiento.setText(cliente?.fechanacimiento ?: "")

        // Perfil

    }

}
