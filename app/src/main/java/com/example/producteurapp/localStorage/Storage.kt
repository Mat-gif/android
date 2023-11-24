package com.example.producteurapp.localStorage

import android.content.Context

class Storage(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("app_stock", Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun saveToPreferences(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun retrieveFromPreferences(key: String, defaultValue: String): String {
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }
}