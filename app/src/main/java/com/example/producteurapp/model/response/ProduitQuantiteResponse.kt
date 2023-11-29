package com.example.producteurapp.model.response

import com.example.producteurapp.model.StatutProduitQuantite

data class ProduitQuantiteResponse(
    val id : Long?,
    val quantite : Int?,
    var statusProduitQuantite : StatutProduitQuantite?
)
