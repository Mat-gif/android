package com.example.producteurapp.ui.compte

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.network.ApiClient
import kotlinx.coroutines.launch

class CompteViewMode(application: Application) : AndroidViewModel(application)  {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    private val _producteurLiveData = MutableLiveData<ProducteurResponse>()
    val producteurLiveData: LiveData<ProducteurResponse> = _producteurLiveData

    private val apiClient: ApiClient by lazy {
        ApiClient(getApplication())
    }

    private val authApiService by lazy {
        apiClient.apiService
    }


    fun updateProduitsList(producteur: ProducteurResponse) {
        _producteurLiveData.postValue(producteur)
    }
    fun profilProducteur() {


        viewModelScope.launch {
            try {
                val response = authApiService.profilProducteur()
                _status.value = "200"
                println("**** "+response)
                updateProduitsList(response)

            } catch (e: Exception) {
                _status.value = e.message
                println("##########"+ e.message)

            }
        }
    }
}