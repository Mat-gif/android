package com.example.producteurapp.viewmodel.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.CommandeDTO
import com.example.producteurapp.network.ApiService
import com.example.producteurapp.viewmodel.AppViewModel
import kotlinx.coroutines.launch

class OrdersViewModel(
    application: Application,
    private var apiService: ApiService,
    private var  navigationEvent: LiveData<AppViewModel.NavigationEvent>,
    private var _navigationEvent : MutableLiveData<AppViewModel.NavigationEvent>,
    private var store : Storage
) : AndroidViewModel(application
){


    /**
     * COMMANDES
     */
    private val _commandes = MutableLiveData<List<CommandeDTO>>()
    val commandes: LiveData<List<CommandeDTO>> = _commandes
    // Si sur un autre Thread
    fun updateCommandes(c: List<CommandeDTO>) { _commandes.postValue(c)}
    // Si sur le thread principal
    fun setCommandes(c: List<CommandeDTO>) { _commandes.value = c }

    private val _commande = MutableLiveData<CommandeDTO>()
    val commande: LiveData<CommandeDTO> = _commande
    // Si sur un autre Thread
    fun updateCommande(c: CommandeDTO) { _commande.postValue(c)}
    // Si sur le thread principal
    fun setCommande(c: CommandeDTO) { _commande.value = c }


    /**
     * RECUPERER LES PRODUITS PUBLIER PAR L'UTILISATEUR
     */
    fun getCommandes() {
        viewModelScope.launch {
            try {
                val response = apiService.afficherCommandes()
                response.commandes?.let { updateCommandes(it) }
                Log.d("GET::/api/producteur/commandes", response.toString())
            } catch (e: Exception) {
                Log.e("GET::/api/producteur/commandes", e.message.toString())
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
                }
            }
        }
    }


    /**
     * Valider ou non la commande
     */
    fun putCommande( request: CommandeDTO){
        viewModelScope.launch {
            try {
                val response = apiService.validerCommande(request)
                getCommandes()
                Log.d("PUT::/api/producteur/commande", "ok")
            } catch (e: Exception) {
                Log.e("PUT::/api/producteur/commande", e.message.toString())
                if(store.isExpired()) {
                    store.clear()
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
                }
            }
        }
    }

}