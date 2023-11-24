package com.example.producteurapp.model

import com.example.producteurapp.ui.notifications.Statut


data class Commande(
     val client : String,
     val date : String,
     val status : Statut
)
