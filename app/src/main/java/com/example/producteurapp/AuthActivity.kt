package com.example.producteurapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.example.producteurapp.databinding.ActivityAuthBinding
import com.example.producteurapp.localStorage.Storage

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var store : Storage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.connexionFragment)

        store= Storage(getApplication())
        val token : String = store.getToken()



        if (token != "" && !store.isExpired())
        {
            startActivity(Intent(this, AppActivity::class.java))
            this.finish()
        }else
        {
            store.clear()
        }

    }

}