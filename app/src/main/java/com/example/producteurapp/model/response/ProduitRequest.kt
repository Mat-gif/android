package com.example.producteurapp.model.response

import java.util.Date

data class ProduitResqponse(
    val id : Long, val nom :String, val prix : Double, val description : String, val quantite : Int, val emailProducteur : String, val  date_publication : Date
)
