package ru.sicampus.bootcamp2026.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sicampus.bootcamp2026.data.dto.auth.LoginRequest
import ru.sicampus.bootcamp2026.data.dto.auth.RegisterRequest
import ru.sicampus.bootcamp2026.data.source.ApiClient

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String, firstName: String, secondName: String): Result<Unit>
    suspend fun validateToken(): Result<Unit>
}

class AuthRepositoryImpl(
    private val client: HttpClient = ApiClient.client
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val request = LoginRequest(email, password)
            // TODO: Заменить на реальный endpoint
            val response = client.post("http://10.0.2.2:8080/api/v1/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, firstName: String, secondName: String): Result<Unit> {
        return try {
            val request = RegisterRequest(email, password, firstName, secondName)
            // TODO: Заменить на реальный endpoint
            val response = client.post("http://10.0.2.2:8080/api/v1/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun validateToken(): Result<Unit> {
        return try {
            // TODO: Реализовать проверку токена
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}