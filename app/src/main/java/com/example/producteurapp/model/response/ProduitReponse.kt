package com.example.producteurapp.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.producteurapp.model.CategorieProduit

@Entity(tableName = "produits")
data class ProduitReponse(
    @PrimaryKey val id : Long?,
    val nom :String?,
    val prix : Double?,
    val description : String?,
    val quantite : Int?,
    val emailProducteur : String?,
    val  date_publication : String?,
    val categorie : CategorieProduit?,
    var isDelete : Boolean?
)
