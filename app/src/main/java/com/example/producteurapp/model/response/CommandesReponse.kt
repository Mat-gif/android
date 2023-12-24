package com.example.producteurapp.model.response

import com.example.producteurapp.model.CommandeDTO


data class CommandesReponse(
    val commandes: List<CommandeDTO>?,
)
