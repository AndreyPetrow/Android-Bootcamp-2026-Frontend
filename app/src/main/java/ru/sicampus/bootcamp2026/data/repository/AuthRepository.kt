package ru.sicampus.bootcamp2026.data.repository

import ru.sicampus.bootcamp2026.data.source.dataSource.AuthDataSource
import ru.sicampus.bootcamp2026.data.source.DataStoreManager
import ru.sicampus.bootcamp2026.domain.entities.User
import ru.sicampus.bootcamp2026.domain.mapper.UserMapper

class AuthRepository(
    private val authDataSource: AuthDataSource
) {
    suspend fun login(email: String, password: String): Result<User> {
        return authDataSource.login(email, password).map { userDto ->
            DataStoreManager.saveCredentials(email, password)
            UserMapper.toDomain(userDto)
        }
    }

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        secondName: String
    ): Result<User> {
        return authDataSource.register(email, password, firstName, secondName).map { userDto ->
            DataStoreManager.saveCredentials(email, password)
            UserMapper.toDomain(userDto)
        }
    }

    suspend fun validateSession(): Result<User> {
        val credentials = DataStoreManager.getCredentials()
        return if (credentials != null) {
            authDataSource.validateSession().map { userDto ->
                UserMapper.toDomain(userDto)
            }
        } else {
            Result.failure(Exception("Нет сохраненных учетных данных"))
        }
    }

    suspend fun logout() {
        DataStoreManager.clearCredentials()
    }
}