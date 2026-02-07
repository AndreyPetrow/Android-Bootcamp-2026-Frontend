package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.core.Constants
import ru.sicampus.bootcamp2026.data.dto.user.UserDto

class UserDataSource {

    suspend fun getUserById(token: String?, userId: Long): Result<UserDto> = withContext(Dispatchers.IO) {
        runCatching {
            val response = ApiClient.client.get(Constants.GET_BY_ID_ENDPOINT + userId) {
                header(HttpHeaders.Authorization, token)
            }

            when (response.status) {
                HttpStatusCode.OK -> response.body<UserDto>()

                HttpStatusCode.NotFound -> error("Такого пользователя не существует.")
                else -> error("Ошибка сервера: ${response.status}")
            }
        }
    }

}