package com.example.producteurapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.model.response.ProduitReponse

@Dao
interface ProfilDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfil(vararg p: ProducteurResponse)
    @Query("SELECT * FROM profil")
    fun getProfil(): ProducteurResponse

}
