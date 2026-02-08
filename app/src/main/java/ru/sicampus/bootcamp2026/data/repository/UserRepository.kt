package ru.sicampus.bootcamp2026.data.repository

import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.data.source.UserDataSource
import ru.sicampus.bootcamp2026.domain.entities.User
import ru.sicampus.bootcamp2026.domain.mapper.UserMapper
import ru.sicampus.bootcamp2026.utils.SettingsUtils


class UserRepository(
    private val userDataSource: UserDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val settingsUtils: SettingsUtils
) {

    suspend fun getUserById(id: Long): Result<User> {
        authLocalDataSource.setToken(settingsUtils.getEmail()!!, settingsUtils.getPassword()!!)

        return userDataSource.getUserById(authLocalDataSource.token, id).map { userDto ->
            UserMapper.toEntity(userDto)
        }
    }

    suspend fun updateUser(
        userId: Long,
        firstName: String,
        secondName: String,
        description: String?,
        position: String?,
        department: String?
    ): Result<User> {
        authLocalDataSource.setToken(settingsUtils.getEmail()!!, settingsUtils.getPassword()!!)

        return userDataSource.updateUser(
            authLocalDataSource.token,
            userId,
            firstName,
            secondName,
            description,
            position,
            department
        ).map { userDto ->
            UserMapper.toEntity(userDto)
        }
    }

}