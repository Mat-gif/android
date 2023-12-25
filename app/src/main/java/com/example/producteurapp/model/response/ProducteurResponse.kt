package com.example.producteurapp.model.response

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.producteurapp.model.CategorieProducteur


@Entity(tableName = "profil")
data class ProducteurResponse(
    @PrimaryKey val   email : String,
    val  nom: String?,
    val  prenom: String?,
    val  adresse: String?,
    val  telephone: String?,
    val  categorie : CategorieProducteur?
)
