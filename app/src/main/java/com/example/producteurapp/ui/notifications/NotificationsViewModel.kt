package com.example.producteurapp.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.producteurapp.model.Commande
import com.example.producteurapp.model.Produit

class NotificationsViewModel : ViewModel() {

    private val _commandeLiveData = MutableLiveData<List<Commande>>()
    val commandeLiveData: LiveData<List<Commande>> = _commandeLiveData

    fun updateCommandesList(commandes: List<Commande>) {
        _commandeLiveData.postValue(commandes)
    }
    // Fonction pour mettre à jour les produits dans le LiveData
    fun setCommandes(commandes: List<Commande>) {
        _commandeLiveData.value = commandes
    }
}