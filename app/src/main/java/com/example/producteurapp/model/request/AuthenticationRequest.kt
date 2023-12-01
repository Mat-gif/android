package com.example.producteurapp.model.request

data class AuthenticationRequest(
    val  email: String,
    val  password: String,
    val tokenApp : String
)
