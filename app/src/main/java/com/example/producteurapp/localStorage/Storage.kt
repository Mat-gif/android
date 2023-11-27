package com.example.producteurapp.localStorage

import android.content.Context
import com.example.producteurapp.model.response.ProducteurResponse


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

fun clear()
{
    editor.clear()
    editor.apply()
}

    fun saveToken(value: String)
    {
        saveToPreferences("token", value)
    }

    fun saveProfil(producteurResponse: ProducteurResponse)
    {
        producteurResponse.nom?.let { saveToPreferences("nom", it) }
        producteurResponse.prenom?.let { saveToPreferences("prenom", it) }
        producteurResponse.adresse?.let { saveToPreferences("adresse", it) }
        producteurResponse.categorie?.let { saveToPreferences("categorie", it.toString()) }
        producteurResponse.email?.let { saveToPreferences("email", it) }
        producteurResponse.telephone?.let { saveToPreferences("telephone", it) }
    }
    fun getProfil(): ProducteurResponse {

        return ProducteurResponse(
                    retrieveFromPreferences("email", ""),
                    retrieveFromPreferences("nom", ""),
                    retrieveFromPreferences("prenom", ""),
                        retrieveFromPreferences("adresse", ""),
                        retrieveFromPreferences("telephone", ""),
                null
        )
    }


    fun getToken() : String
    {
        return retrieveFromPreferences("token", "")
    }
}