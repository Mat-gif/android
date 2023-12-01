package com.example.producteurapp.model.request

import com.example.producteurapp.model.CategorieProduit

data class ProduitRequest(
    val id: Long,
    val nom:String,
    val prix: Double,
    val description: String,
    val quantite: Int,
    val categorie : CategorieProduit
)
