package com.example.producteurapp.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Permet d'intercepeter les requettes http afin d'ajouter le JWT token
 */
class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}