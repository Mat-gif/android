package com.example.producteurapp.http

import android.content.Context
import com.example.producteurapp.localStorage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Http(context : Context){
    val url : String = "https://75b8-23-90-237-158.ngrok.io/api/"
    val client : HttpClient = HttpClient()
    val json = Json { encodeDefaults = true }
    val token : String = Storage(context).retrieveFromPreferences("token", "")


     suspend fun decode(response: HttpResponse):Map<String, String>
    {
        return Json { ignoreUnknownKeys = true }.decodeFromString(response.body());
    }

    suspend inline fun <reified T : Any> request_post(service: String, body: T): HttpResponse {
        if (token==""){
            return client.post("%s%s".format(url, service)) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(body))
            }
        }
        return client.post("%s%s".format(url, service)) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(body))
        }
    }

    suspend fun request_get(service: String): HttpResponse {
        if (token==""){
            return client.get("%s%s".format(url, service))
        }
        return client.get("%s%s".format(url, service)){header("Authorization", "Bearer $token")}
    }

    suspend inline fun <reified T : Any> request_put(service: String, body: T): HttpResponse {
        if (token==""){
            return client.put("%s%s".format(url, service)) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(body))
            }
        }
        return client.put("%s%s".format(url, service)) {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(body))
        }
    }

    suspend inline fun <reified T : Any> request_delete(service: String, body: T): HttpResponse {
        if (token==""){
            return client.delete("%s%s".format(url, service)) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(body))
            }
        }
        return client.delete("%s%s".format(url, service)) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(body))
        }
    }
}