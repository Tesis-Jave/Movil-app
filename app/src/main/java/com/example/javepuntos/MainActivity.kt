package com.example.javepuntos

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.javepuntos.databinding.ActivityMainBinding
import com.example.javepuntos.model.Cliente
import com.example.javepuntos.model.TokenResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<AppCompatImageButton>(R.id.imageButton7).setOnClickListener {
            finish()
        }

        val gson = Gson()
        val tokenResponse: TokenResponse = gson.fromJson(intent.getStringExtra("response_data"), object : TypeToken<TokenResponse>() {}.type)

        val token = tokenResponse.token
        val id = tokenResponse.id



        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.title = "Jave Puntos"

        binding.buttonEP.setOnClickListener {
            val intent=Intent(this@MainActivity,Transferir::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id)
            startActivity(intent)
        }

        binding.buttonHistorial.setOnClickListener {
            val intent=Intent(this@MainActivity,Historial::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("id_cliente",id)
            startActivity(intent)
        }

        binding.perfilPuntos.setOnClickListener {
            val intent = Intent(this@MainActivity,EditarPerfilActivity::class.java)
            intent.putExtra("response_data",token)
            intent.putExtra("idCliente",id)
            startActivity(intent)
        }
        binding.botonMapa.setOnClickListener{
            val intent = Intent(this, MapaCafeteriasActivity::class.java)
            // Iniciar la actividad
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        // Forzar la orientación vertical al volver a esta actividad
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}