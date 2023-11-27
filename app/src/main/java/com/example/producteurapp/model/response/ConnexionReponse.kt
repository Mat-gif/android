package com.example.producteurapp.model.response

import com.example.producteurapp.model.CategorieProducteur

data class ConnexionReponse(
    val token: String?,
    val producteur: ProducteurResponse?
)
