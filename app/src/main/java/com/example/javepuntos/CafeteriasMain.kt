package com.example.javepuntos

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.model.Cafeteria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CafeteriasMain : AppCompatActivity() {

    override fun onDestroy() {
        // Detener recursos de red, hilos u otros recursos aquí
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeterias_main)

        // Recuperar el token para usaro en las consultas
        val sharedPreferences = getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN_KEY", null)

        val url = "http://$BASE_URL/cafeterias/"

        // Llamar a un AsyncTask para realizar la solicitud de red
        val networkTask = NetworkTask(this)
        networkTask.execute(url, token)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class NetworkTask(private val context: Context) : AsyncTask<String, Void, List<Cafeteria>>() {
        override fun doInBackground(vararg params: String?): List<Cafeteria> {
            val url = params[0]
            val token = params[1]

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer $token")
                .build()

            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseData = response.body()?.string()
                    println(responseData)
                    val gson = Gson()
                    return gson.fromJson(
                        responseData,
                        object : TypeToken<List<Cafeteria>>() {}.type
                    )

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return emptyList()
        }

        override fun onPostExecute(result: List<Cafeteria>) {
            // Actualizar la interfaz de usuario con los datos
            val gridLayout: GridLayout = findViewById(R.id.gridLayout)
            val adapter = CafeteriasAdapter(context, result)

            val columnCount = 2 // Puedes ajustar el número de columnas según tus necesidades

            for (i in 0 until adapter.count) {
                val cafeteriaView = adapter.getView(i, null, gridLayout)

                // Crea parámetros para la vista
                val params = GridLayout.LayoutParams()

                // Calcula la columna y fila en función de las columnas
                val columna = i % columnCount
                val fila = i / columnCount

                // Alinea el elemento en el centro de su celda
                params.setGravity(Gravity.CENTER)

                // Establece las especificaciones de columna y fila
                params.columnSpec = GridLayout.spec(columna)
                params.rowSpec = GridLayout.spec(fila)

                // Establece los parámetros en la vista
                cafeteriaView.layoutParams = params

                // Agrega la vista al GridLayout
                gridLayout.addView(cafeteriaView)
            }

        }

    }
}

