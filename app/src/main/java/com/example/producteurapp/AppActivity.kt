package com.example.producteurapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.producteurapp.databinding.ActivityMainBinding
import com.example.producteurapp.localStorage.Storage

class AppActivity : AppCompatActivity() {


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



    }
}