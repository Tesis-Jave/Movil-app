package com.example.javepuntos

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.javepuntos.databinding.LoginBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_profile)

        // Configura el NavController y la AppBarConfiguration
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_profile_content) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)


    }

    // Agrega esta función para habilitar la navegación hacia atrás en la actividad
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
