package com.example.producteurapp.model.response

import com.example.producteurapp.model.CategorieProduit


data class ProduitReponse(
    val id : Long?,
    val nom :String?,
    val prix : Double?,
    val description : String?,
    val quantite : Int?,
    val emailProducteur : String?,
    val  date_publication : String?,
    val categorie : CategorieProduit?
)
