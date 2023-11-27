package com.example.producteurapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.producteurapp.databinding.ActivityMainBinding

class AppActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var appViewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        val navController = findNavController(R.id.nav_app)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_accueil, R.id.navigation_publier, R.id.navigation_notifications, R.id.navigation_compte
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)


        /**
         * initialisation du ViewModel
         */
         appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        appViewModel.navigationEvent.observe(this) { event ->
            when (event) {
                is AppViewModel.NavigationEvent.LaunchNewActivity -> {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}