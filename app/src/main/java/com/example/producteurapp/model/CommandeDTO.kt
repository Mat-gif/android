package com.example.producteurapp.model

import com.example.producteurapp.model.request.Request
import com.example.producteurapp.model.response.ProduitQuantiteResponse

data class CommandeDTO(
    val id: Long?,
    var status: StatutCommande?,
    val client_id: String?,
    var produits: List<ProduitQuantiteResponse>?,
    val date: String?
) : Request();