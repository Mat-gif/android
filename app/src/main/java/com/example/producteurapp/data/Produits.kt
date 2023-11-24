package com.example.producteurapp.data

import com.example.producteurapp.model.Produit

class Produits {
    fun list(): ArrayList<Produit>
    {
        return arrayListOf(
            Produit("Tomate", "2.30", "15/11/2023"),
            Produit("Patate", "5.30", "14/10/2023"),
            Produit("Banane", "1.30", "10/11/2023"),
            Produit("Salade", "0.90", "09/11/2023")
        )
    }
}
