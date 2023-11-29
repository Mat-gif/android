package com.example.producteurapp.model.request

data class RegisterRequest(
    val  email: String,
    val  password: String,
    val  nom : String,
    val tokenApp : String
)
