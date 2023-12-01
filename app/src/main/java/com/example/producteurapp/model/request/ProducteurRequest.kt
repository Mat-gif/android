package com.example.producteurapp.model.request

import com.example.producteurapp.model.CategorieProducteur

data class ProducteurRequest(
    val  nom: String,
    val  prenom: String,
    val  adresse: String,
    val  telephone: String,
    val  categorie : CategorieProducteur
)
