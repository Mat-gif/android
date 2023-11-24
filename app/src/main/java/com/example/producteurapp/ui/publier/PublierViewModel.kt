package com.example.producteurapp.ui.publier

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.network.ApiClient

import kotlinx.coroutines.launch

class PublierViewModel(application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status


    private val apiClient: ApiClient by lazy {
        ApiClient(getApplication())
    }

    private val authApiService by lazy {
        apiClient.apiService
    }

    fun publierToApi(produitRequest: ProduitRequest ) {
        println(produitRequest)

        viewModelScope.launch {
            try {
                val response = authApiService.publierProduit(produitRequest)
                _status.value = "200"
                println("**** "+response)


            } catch (e: Exception) {
                _status.value = e.message
                println("#### "+e.message)
            }
        }
    }
}
