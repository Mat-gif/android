package com.example.producteurapp


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.producteurapp.databinding.ActivityMainBinding
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.ui.compte.EditerCompteFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.FirebaseApp

class AppActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var appViewModel: AppViewModel

    private lateinit var store : Storage

    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        store= Storage(this)

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


        /**
         * Gestion quand token expiré => redirection Page de connexion
         */
        appViewModel.navigationEvent.observe(this) { event ->
            when (event) {
                is AppViewModel.NavigationEvent.LaunchNewActivity -> {

                    AlertDialog.Builder(this)
                        .setMessage("Reconnexion nécessaire")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                            val intent = Intent(this, AuthActivity::class.java)
                            startActivity(intent)
                        }
                        .create().show()

                }

                else -> {}
            }
        }

        findViewById<ExtendedFloatingActionButton>(R.id.logout).setOnClickListener {
            store.clear()
            startActivity(Intent(this, AuthActivity::class.java))
            this.finish()
        }



    }
}