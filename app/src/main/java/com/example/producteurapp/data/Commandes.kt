package com.example.producteurapp.data

import com.example.producteurapp.model.Commande
import com.example.producteurapp.ui.notifications.Statut

class Commandes {
    fun list(): ArrayList<Commande> {
        return arrayListOf<Commande>(
            Commande("Toto","09/11/2023", Statut.EN_ATTENTE),
            Commande("Tata","14/10/2023", Statut.VALIDEE),
            Commande("Titi", "10/11/2023", Statut.VALIDEE),
            Commande("Tutu","09/11/2023", Statut.REFUSEE),
        )
    }
}