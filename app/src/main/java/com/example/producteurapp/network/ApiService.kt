package com.example.producteurapp.network



import com.example.producteurapp.model.request.AuthenticationRequest
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.request.RegisterRequest
import com.example.producteurapp.model.CommandeDTO
import com.example.producteurapp.model.response.CommandesReponse
import com.example.producteurapp.model.response.ConnexionReponse
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.model.response.ProduitsReponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Connexion a l'api
 */

interface ApiService {
    @GET("/api/producteur/produit")
    suspend fun afficherProduits() : ProduitsReponse
    @GET("/api/producteur/profil")
    suspend fun profilProducteur() : ProducteurResponse
    @POST("/api/producteur/produit")
    suspend fun publierProduit(@Body produitRequest: ProduitRequest): ProduitReponse
    @POST("/api/auth/producteur/authenticate")
    suspend fun connexion(@Body authenticationRequest: AuthenticationRequest): ConnexionReponse
    @PUT("/api/producteur/profil")
    suspend fun modifierProfil(@Body producteurRequest: ProducteurRequest) : ProducteurResponse
    @POST("/api/auth/producteur/register")
    suspend fun enregistrement(@Body registerRequest: RegisterRequest): ConnexionReponse
    @GET("/api/producteur/commandes")
    suspend fun afficherCommandes() : CommandesReponse
    @PUT("/api/producteur/commande")
    suspend fun validerCommande(@Body request : CommandeDTO)
    @PUT("/api/producteur/produit")
    suspend fun modifierProduit(@Body produitRequest: ProduitRequest) : ProduitReponse
}

