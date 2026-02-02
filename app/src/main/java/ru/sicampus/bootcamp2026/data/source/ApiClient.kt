package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

interface ApiClient {
    suspend fun <T> get(endpoint: String): T
    suspend fun <T, R> post(endpoint: String, body: R): T
    suspend fun <T, R> put(endpoint: String, body: R): T
    suspend fun <T> delete(endpoint: String): T
    fun updateAuthToken(token: String)
}

class ApiClientImpl : ApiClient {

    private var authToken: String? = null

    // Создаем клиент как свойство класса
    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = TimeUnit.SECONDS.toMillis(30)
                connectTimeoutMillis = TimeUnit.SECONDS.toMillis(30)
                socketTimeoutMillis = TimeUnit.SECONDS.toMillis(30)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                url(Config.BASE_URL)
                contentType(ContentType.Application.Json)
                authToken?.let {
                    header(Config.AUTHORIZATION_HEADER, "${Config.BEARER_PREFIX}$it")
                }
            }
        }
    }

    override suspend fun <T> get(endpoint: String): T {
        TODO("Not yet implemented")
    }

    override suspend fun <T, R> post(endpoint: String, body: R): T {
        TODO("Not yet implemented")
    }

    override suspend fun <T, R> put(endpoint: String, body: R): T {
        TODO("Not yet implemented")
    }

    override suspend fun <T> delete(endpoint: String): T {
        TODO("Not yet implemented")
    }

    override fun updateAuthToken(token: String) {
        TODO("Not yet implemented")
    }
}