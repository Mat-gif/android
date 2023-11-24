package com.example.producteurapp.model.request

data class ProduitRequest(
    val id: Long,
    val nom:String,
    val prix: Double,
    val description: String,
    val quantite: Int
)
