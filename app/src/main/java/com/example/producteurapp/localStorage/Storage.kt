package com.example.producteurapp.localStorage

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.producteurapp.model.response.ProducteurResponse
import com.google.common.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.Base64


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


    fun decodeToken(): String {
        val jwt = getToken()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)


            "${parsePayloadToMap(payload)["exp"]}"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
    fun parsePayloadToMap(payloadString: String): Map<String, Any> {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Map<*, *>>? = moshi.adapter(
            Map::class.java
        )

        return try {
            jsonAdapter?.fromJson(payloadString) as Map<String, Any>? ?: emptyMap()
        } catch (e: Exception) {
            emptyMap() // Si la conversion échoue, renvoyer une carte vide
        }
    }


    fun isExpired() : Boolean
    {
        val currentTimeSeconds = System.currentTimeMillis() / 1000 // Temps actuel en secondes
        if (currentTimeSeconds > decodeToken().toDouble().toLong()) {
            Log.d("EXPIRED", true.toString())
            return true;
        } else {
            println("Le token est valide.")
            Log.d("EXPIRED", false.toString())
            return false;
        }

    }
}