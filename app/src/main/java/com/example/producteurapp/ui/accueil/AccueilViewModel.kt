package com.example.producteurapp.ui.accueil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.producteurapp.model.Produit

class AccueilViewModel : ViewModel() {

    private val _produitLiveData = MutableLiveData<List<Produit>>()
    val produitLiveData: LiveData<List<Produit>> = _produitLiveData

    fun updateProduitsList(products: List<Produit>) {
        _produitLiveData.postValue(products)
    }
    // Fonction pour mettre à jour les produits dans le LiveData
    fun setProduits(products: List<Produit>) {
        _produitLiveData.value = products
    }

}