package ru.sicampus.bootcamp2026.data.repository

import ru.sicampus.bootcamp2026.data.source.UserDataSource
import ru.sicampus.bootcamp2026.domain.entities.User
import ru.sicampus.bootcamp2026.domain.entities.UserMini
import ru.sicampus.bootcamp2026.domain.mapper.UserMapper


class UserRepository(
    private val userDataSource: UserDataSource,
) {

    suspend fun getUserById(id: Long): Result<User> {
        return userDataSource.getUserById(id).map { userDto ->
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
        return userDataSource.updateUser(
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

    suspend fun searchUsers(
        searchQuery: String,
        page: Int = 0,
        size: Int = 10
    ): Result<List<UserMini>> {
        return userDataSource.searchUsers(
            searchQuery,
            page,
            size
        ).map { userMiniDtos ->
            userMiniDtos.map { UserMapper.toDomain(it) }
        }
    }

}