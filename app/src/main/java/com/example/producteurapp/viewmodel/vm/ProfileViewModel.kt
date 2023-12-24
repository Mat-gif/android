package com.example.producteurapp.viewmodel.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.network.ApiService
import com.example.producteurapp.viewmodel.AppViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application,
    private var apiService: ApiService,
    private var  navigationEvent: LiveData<AppViewModel.NavigationEvent>,
    private var _navigationEvent : MutableLiveData<AppViewModel.NavigationEvent>,
    private var store : Storage
) : AndroidViewModel(application
){

    /**
     * PROFIL
     */
    private val _profil = MutableLiveData<ProducteurResponse>()
    val profil: LiveData<ProducteurResponse> = _profil
    // Si sur un autre Thread
    fun updateProfil(p: ProducteurResponse) { _profil.postValue(p)}
    // Si sur le thread principal
    fun setProfil(p: ProducteurResponse) { _profil.value = p }

    /**
     * RECUPERER LE PROFIL DE L'UTILISATEUR
     */
    fun getProducteur() {
        viewModelScope.launch {
            try {
                val response = apiService.profilProducteur()
                updateProfil(response)
                store.saveProfil(response)
                Log.d("GET::/api/producteur/profil", response.toString())
            } catch (e: Exception) {
                Log.e("GET::/api/producteur/profil", e.message.toString())
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
                }

            }
        }
    }

    /**
     * MODIFIER LE PROFIL DE L'UTILISATEUR
     */
    fun putProducteur(producteurRequest: ProducteurRequest) {
        viewModelScope.launch {
            try {
                val response = apiService.modifierProfil(producteurRequest)
                updateProfil(response)
                store.saveProfil(response)
                Log.d("PUT::/api/producteur/profil", response.toString())
                println("#####$response")
            } catch (e: Exception) {
                Log.e("PUT::/api/producteur/profil", e.message.toString())
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
                }

            }
        }
    }
}