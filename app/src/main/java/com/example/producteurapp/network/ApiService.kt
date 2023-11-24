package com.example.producteurapp.network


import com.example.producteurapp.model.Commande
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.ConnexionReponse
import com.example.producteurapp.model.response.ProduitResqponse
import com.example.producteurapp.request.AuthenticationRequest
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("/api/producteur/produit")
    suspend fun publierProduit(@Body produitRequest: ProduitRequest): ProduitResqponse
    @POST("/api/auth/producteur/authenticate")
    suspend fun connexion(@Body authenticationRequest: AuthenticationRequest ): ConnexionReponse

}

