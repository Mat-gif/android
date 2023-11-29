package com.example.producteurapp.data

import com.example.producteurapp.model.Commande
import com.example.producteurapp.model.StatutCommande

class Commandes {
    fun list(): ArrayList<Commande> {
        return arrayListOf<Commande>(
            Commande("Toto","09/11/2023", StatutCommande.EN_ATTENTE_DE_VALIDATION),
            Commande("Tata","14/10/2023", StatutCommande.VALIDE),
            Commande("Titi", "10/11/2023", StatutCommande.VALIDE),
            Commande("Tutu","09/11/2023", StatutCommande.REFUS),
        )
    }
}