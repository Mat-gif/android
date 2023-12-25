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
import com.example.producteurapp.network.ApiClient
import com.example.producteurapp.network.ApiService
import com.example.producteurapp.room.AppDatabase
import com.example.producteurapp.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application,

    private var store : Storage
) : AndroidViewModel(application
){
    private val _navigationEvent = MutableLiveData<AppViewModel.NavigationEvent>()
    val navigationEvent: LiveData<AppViewModel.NavigationEvent> = _navigationEvent

    /**
     * initalisation de http client
     */
    private val apiClient: ApiClient by lazy { ApiClient(getApplication()) }
    private val apiService by lazy { apiClient.apiService }
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
                /**
                 * Je recupere mon profil dans la base locale (car mis a jour uniquement quand update)
                 */
                viewModelScope.launch(Dispatchers.IO) {

                    var response = AppDatabase.getDatabase(getApplication()).profilDao().getProfil(store.getProfil().email)
                    /**
                     * si valeur null j'initialise mon utilisateur lors de la premiere connexion en local
                     */
                    if (response == null ){
                        response = apiService.profilProducteur()
                        println("appel-api::getprofil")
                    }
                    updateProfil(response)
                    store.saveProfil(response)
                    Log.d("GET::/api/producteur/profil", response.toString())
                }
//
//                val response = apiService.profilProducteur()
//                updateProfil(response)
//                store.saveProfil(response)
//                Log.d("GET::/api/producteur/profil", response.toString())
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
                store.saveProfil(response) // on ne touche pas mais plus forcement utile
                /**
                 * je save en local le profil modifié
                 */
                viewModelScope.launch(Dispatchers.IO) {
                    AppDatabase.getDatabase(getApplication()).profilDao().insertProfil(response)
                }
                Log.d("PUT::/api/producteur/profil", response.toString())
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