package ru.sicampus.bootcamp2026.data.source.dataSource

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.dto.auth.RegisterRequest
import ru.sicampus.bootcamp2026.data.dto.user.UserDto
import ru.sicampus.bootcamp2026.data.source.ApiClient

class AuthNetworkDataSource {
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

    suspend fun checkAuth(token: String?): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            val result = ApiClient.client.get("/auth/login"){
                header(HttpHeaders.Authorization, token)
            }
            result.status == HttpStatusCode.OK
        }
    }
}