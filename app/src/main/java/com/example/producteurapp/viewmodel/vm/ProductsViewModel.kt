package com.example.producteurapp.viewmodel.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.network.ApiClient
import com.example.producteurapp.network.ApiService
import com.example.producteurapp.room.AppDatabase
import com.example.producteurapp.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel(
    application: Application,
    private var apiService: ApiService,
    private var  navigationEvent: LiveData<AppViewModel.NavigationEvent>,
    private var _navigationEvent : MutableLiveData<AppViewModel.NavigationEvent>,
    private var store : Storage) : AndroidViewModel(application
    ){

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
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
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
                // ajout en base

                viewModelScope.launch(Dispatchers.IO) {
                    // Utilisez la coroutine IO pour effectuer des opérations de base de données
                    response.produits.forEach { p ->
                        AppDatabase.getDatabase(getApplication()).produitDao().insertAllProduducts(p)
                    }
                    val allProducts = AppDatabase.getDatabase(getApplication()).produitDao().getAllProduducts()
                    Log.d("TESST", allProducts.toString())
                }
//




                Log.d("GET::/api/producteur/produit", response.toString())
            } catch (e: Exception) {
                Log.e("GET::/api/producteur/produit", e.message.toString())
                if(store.isExpired()){
                    store.clear()
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
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
                    _navigationEvent.value = AppViewModel.NavigationEvent.LaunchNewActivity
                }

            }
        }
    }


}