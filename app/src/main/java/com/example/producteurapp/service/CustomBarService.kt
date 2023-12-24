package com.example.producteurapp.service

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.producteurapp.R

class CustomBarService(private val fragment: FragmentActivity, private val layout: Int){
    fun init(){
        // Modifier la bar d'action
        val actionBar = (fragment as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(layout)
    }

}