package com.example.producteurapp.model.response

import com.example.producteurapp.model.CategorieProducteur

data class ProducteurResponse(
    val   email : String?,
    val  nom: String?,
    val  prenom: String?,
    val  adresse: String?,
    val  telephone: String?,
    val  categorie : CategorieProducteur?
)
