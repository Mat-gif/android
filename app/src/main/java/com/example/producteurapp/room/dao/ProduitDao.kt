package com.example.producteurapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.producteurapp.model.response.ProduitReponse

@Dao
interface ProduitDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(vararg p: ProduitReponse)
    @Update
    fun updateProduct(vararg produits: ProduitReponse)


    @Query("DELETE FROM produits  WHERE id = :productId")
    fun deleteProductById(productId: Long)

    @Query("SELECT * FROM produits p WHERE p.emailProducteur = :email")
    fun getAllProduducts(email : String): List<ProduitReponse>

    @Query("SELECT * FROM produits")
    fun getAll(): List<ProduitReponse>



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