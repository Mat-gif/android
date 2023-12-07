package com.example.producteurapp

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.CommandeReponse
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


    private val _produit = MutableLiveData<ProduitReponse>()
    val produit: LiveData<ProduitReponse> = _produit
    // Si sur un autre Thread
    fun updateProduit(p: ProduitReponse) { _produit.postValue(p)}
    // Si sur le thread principal
    fun setProduit(p: ProduitReponse) { _produit.value = p }


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
    private val _commandes = MutableLiveData<List<CommandeReponse>>()
    val commandes: LiveData<List<CommandeReponse>> = _commandes
    // Si sur un autre Thread
    fun updateCommandes(c: List<CommandeReponse>) { _commandes.postValue(c)}
    // Si sur le thread principal
    fun setCommandes(c: List<CommandeReponse>) { _commandes.value = c }

    private val _commande = MutableLiveData<CommandeReponse>()
    val commande: LiveData<CommandeReponse> = _commande
    // Si sur un autre Thread
    fun updateCommande(c: CommandeReponse) { _commande.postValue(c)}
    // Si sur le thread principal
    fun setCommande(c: CommandeReponse) { _commande.value = c }




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
                getProduits()
            } catch (e: Exception) {
                Log.e("POST::/api/producteur/produit", e.message.toString())
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
                }
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
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
                }
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
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
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
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
                }

            }
        }
    }

    fun putProduit(produitRequest: ProduitRequest) {
        viewModelScope.launch {
            try {
                val response = apiService.modifierProduit(produitRequest)
                getProduits()
                Log.d("PUT::/api/producteur/produit", response.toString())
                println("#####$response")
            } catch (e: Exception) {
                Log.e("PUT::/api/producteur/produit", e.message.toString())
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
                }

            }
        }
    }

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
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
                }
            }
        }
    }

    /**
     * Valider ou non la commande
     */
    fun putCommande( request: CommandeReponse){
        viewModelScope.launch {
            try {
                val response = apiService.validerCommande(request)
                getCommandes()
                Log.d("PUT::/api/producteur/commande", "ok")
            } catch (e: Exception) {
                Log.e("PUT::/api/producteur/commande", e.message.toString())
                if(store.isExpired()) {
                    store.clear()
                    _navigationEvent.value = NavigationEvent.LaunchNewActivity
                }
            }
        }
    }
    /**
     * AU CHARGEMENT DE L'ACTIVITE
     */
    init {
        store= Storage(getApplication())
        Log.d("HELLOWORD",store.decodeToken())
        Log.d("HELLOWORD",store.isExpired().toString())
        getProducteur()
        getProduits()
        getCommandes()
    }


    sealed class NavigationEvent {
        object LaunchNewActivity : NavigationEvent()
    }


}