package com.example.producteurapp.model.response

import com.example.producteurapp.model.StatutCommande

data class CommandeReponse(
    val id: Long?,
    val status: StatutCommande?,
    val client_id: String?,
    val produits: List<ProduitQuantiteResponse>?,
    val date: String?
)
