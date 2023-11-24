package com.example.producteurapp.ui.accueil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.Produit
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.network.ApiClient
import com.example.producteurapp.request.AuthenticationRequest
import kotlinx.coroutines.launch

class AccueilViewModel(application: Application) : AndroidViewModel(application)  {

    private val _produitLiveData = MutableLiveData<List<ProduitReponse>>()
    val produitLiveData: LiveData<List<ProduitReponse>> = _produitLiveData
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    fun updateProduitsList(products: List<ProduitReponse>) {
        _produitLiveData.postValue(products)
    }
    // Fonction pour mettre à jour les produits dans le LiveData
    fun setProduits(products: List<ProduitReponse>) {
        _produitLiveData.value = products
    }

    private val apiClient: ApiClient by lazy {
        ApiClient(getApplication())
    }

    private val authApiService by lazy {
        apiClient.apiService
    }

    init {
        getProduits()
    }


    fun getProduits() {

        viewModelScope.launch {
            try {
                val response = authApiService.afficherProduits()
                _status.value = "200"
                println("**********"+response)
                updateProduitsList(response.produits)


            } catch (e: Exception) {
                _status.value = e.message
                println("###############"+e.message)
            }
        }
    }

    fun performSearch(query: String?) {
        // Vérifiez si query n'est pas nul ou vide
        if (!query.isNullOrEmpty()) {
            val filteredList = _produitLiveData.value?.filter { produit ->
                produit.nom?.contains(query, ignoreCase = true) ?: return  // Exemple : Recherche par nom de produit
            }
            if (filteredList != null) {
                updateProduitsList(filteredList)
            }
        } else {
            // Si la recherche est vide, montrez la liste complète
            _produitLiveData.value?.let { updateProduitsList(it) }
        }
    }


}