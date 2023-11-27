package com.example.producteurapp

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.Commande
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.network.ApiClient
import kotlinx.coroutines.launch



class AppViewModel(application: Application) : AndroidViewModel(application) {

    // pour stocker certaine informations
    private lateinit var store : Storage

    private val _navigationEvent = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent

    /**
     * PRODUITS
     */
    private val _produits = MutableLiveData<List<ProduitReponse>>()
    val produits: LiveData<List<ProduitReponse>> = _produits
    // Si sur un autre Thread
    fun updateProduits(p: List<ProduitReponse>) { _produits.postValue(p)}
    // Si sur le thread principal
    fun setProduits(p: List<ProduitReponse>) { _produits.value = p }


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
     * COMMANDES
     */
    private val _commandes = MutableLiveData<List<Commande>>()
    val commandes: LiveData<List<Commande>> = _commandes
    // Si sur un autre Thread
    fun updateCommandes(c: List<Commande>) { _commandes.postValue(c)}
    // Si sur le thread principal
    fun setCommandes(c: List<Commande>) { _commandes.value = c }



    /**
     * initalisation de http client
     */
    private val apiClient: ApiClient by lazy {ApiClient(getApplication()) }
    private val apiService by lazy { apiClient.apiService }

    /**
     * PUBLIER UN NOUVEAU PRODUIT
     */
    fun postProduit(produitRequest: ProduitRequest) {
        viewModelScope.launch {
            try {
                val response = apiService.publierProduit(produitRequest)
                Log.d("POST::/api/producteur/produit", response.toString())
            } catch (e: Exception) {
                Log.e("POST::/api/producteur/produit", e.message.toString())
                store.clear()
                _navigationEvent.value = NavigationEvent.LaunchNewActivity

            }
        }
    }

    /**
     * RECUPERER LES PRODUITS PUBLIER PAR L'UTILISATEUR
     */
    fun getProduits() {
        viewModelScope.launch {
            try {
                val response = apiService.afficherProduits()
                updateProduits(response.produits)
                Log.d("GET::/api/producteur/produit", response.toString())
            } catch (e: Exception) {
                Log.e("GET::/api/producteur/produit", e.message.toString())
                store.clear()
                _navigationEvent.value = NavigationEvent.LaunchNewActivity

            }
        }
    }
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
                store.clear()
                _navigationEvent.value = NavigationEvent.LaunchNewActivity


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
                store.clear()
                _navigationEvent.value = NavigationEvent.LaunchNewActivity

            }
        }
    }

    /**
     * AU CHARGEMENT DE L'ACTIVITE
     */
    init {
        store= Storage(getApplication())
        getProducteur()
        getProduits()
    }


    sealed class NavigationEvent {
        object LaunchNewActivity : NavigationEvent()
    }


}