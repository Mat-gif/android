package com.example.producteurapp.model.response

import com.example.producteurapp.model.StatutCommande

data class CommandeReponse(
    val id: Long?,
    var status: StatutCommande?,
    val client_id: String?,
    var produits: List<ProduitQuantiteResponse>?,
    val date: String?
) {

}
