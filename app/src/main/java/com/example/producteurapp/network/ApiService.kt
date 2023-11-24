package com.example.producteurapp.network



import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.ConnexionReponse
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.model.response.ProduitsReponse
import com.example.producteurapp.request.AuthenticationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("/api/producteur/produit")
    suspend fun afficherProduits() : ProduitsReponse

    @GET("/api/producteur/profil")
    suspend fun profilProducteur() : ProducteurResponse

    @POST("/api/producteur/produit")
    suspend fun publierProduit(@Body produitRequest: ProduitRequest): ProduitReponse
    @POST("/api/auth/producteur/authenticate")
    suspend fun connexion(@Body authenticationRequest: AuthenticationRequest ): ConnexionReponse

}

