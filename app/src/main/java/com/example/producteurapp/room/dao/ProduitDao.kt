package com.example.producteurapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.producteurapp.model.response.ProduitReponse

@Dao
interface ProduitDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(vararg p: ProduitReponse)

    @Query("SELECT * FROM produits p WHERE p.emailProducteur = :email")
    fun getAllProduducts(email : String): List<ProduitReponse>

//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertProduits(produits: List<ProduitReponse>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertProduit(produit: ProduitReponse)
//
//    @Query("SELECT * FROM produits") // Remplacez "produitreponse" par le nom réel de votre table
//    fun getAllProduits(): List<ProduitReponse>

}