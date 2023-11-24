package com.example.producteurapp.network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage

import com.example.producteurapp.request.AuthenticationRequest

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

    fun connexionToApi(authRequest : AuthenticationRequest) {

        viewModelScope.launch {
            try {
                val connexionResponse = authApiService.connexion(authRequest)
                _status.value = "200"
                store= Storage(getApplication())
                store.saveToken(connexionResponse.token)


            } catch (e: Exception) {
                _status.value = e.message
            }
        }
    }
}
