package com.example.producteurapp.network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.AuthenticationRequest
import com.example.producteurapp.model.request.RegisterRequest
import com.example.producteurapp.model.response.ProducteurResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

import kotlinx.coroutines.launch

class ConnexionViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var store : Storage
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status


    private val apiClient: ApiClient by lazy {
        ApiClient(getApplication())
    }

    private val authApiService by lazy {
        apiClient.apiService
    }

    init {
        store= Storage(getApplication())
    }
    fun connexionToApi(authRequest : AuthenticationRequest) {
        _status.value = "400"
        viewModelScope.launch {

            try {
                val connexionResponse = authApiService.connexion(authRequest)
                _status.value = "200"
                store= Storage(getApplication())
                connexionResponse.token?.let { store.saveToken(it) }
                connexionResponse.producteur?.let { store.saveProfil(it) }
                println("#####$connexionResponse")


            } catch (e: Exception) {
                _status.value = e.message
                store.clear()
                println("#####"+e.toString())
            }
        }
    }

    fun enregistrementToApi(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = authApiService.enregistrement(registerRequest)
                _status.value = "200"
                println("#####$response")


            } catch (e: Exception) {
                _status.value = e.message
                store.clear()
                println("#####"+e.toString())
            }
        }


    }
}
