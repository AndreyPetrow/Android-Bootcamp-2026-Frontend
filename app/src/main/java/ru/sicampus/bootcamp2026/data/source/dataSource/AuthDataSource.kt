package ru.sicampus.bootcamp2026.data.source.dataSource

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.dto.auth.LoginRequest
import ru.sicampus.bootcamp2026.data.dto.auth.RegisterRequest
import ru.sicampus.bootcamp2026.data.dto.user.UserDto
import ru.sicampus.bootcamp2026.data.source.ApiClient

class AuthDataSource {
    suspend fun login(email: String, password: String): Result<UserDto> = withContext(Dispatchers.IO) {
        runCatching {
            val result = ApiClient.client.post("auth/login") {
                setBody(LoginRequest(email, password))
            }
            result.body<UserDto>()
        }
    }

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        secondName: String
    ): Result<UserDto> = withContext(Dispatchers.IO) {
        runCatching {
            val result = ApiClient.client.post("auth/register") {
                setBody(RegisterRequest(email, password, firstName, secondName))
            }
            result.body<UserDto>()
        }
    }

    suspend fun validateSession(): Result<UserDto> = withContext(Dispatchers.IO) {
        runCatching {
            val result = ApiClient.client.get("auth/login")
            result.body<UserDto>()
        }
    }
}