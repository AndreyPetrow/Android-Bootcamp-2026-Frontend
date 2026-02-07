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
}


//interface UserRepository {
//    suspend fun getUserById(id: Long): Result<UserDto>
//    suspend fun searchUsers(search: String): Result<List<UserMiniDto>>
//    suspend fun updateUser(userUpdateDto: UserUpdateDto): Result<UserDto>
//}
//
//class UserRepositoryImpl(
//    private val client: HttpClient = ApiClient.client
//) : UserRepository {
//
//    override suspend fun getUserById(id: Long): Result<UserDto> {
//        return try {
//            // TODO: Заменить на реальный endpoint
//            val response = client.get("http://10.0.2.2:8080/api/v1/user/$id")
//            val userDto = response.body<UserDto>()
//            Result.success(userDto)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    override suspend fun searchUsers(search: String): Result<List<UserMiniDto>> {
//        return try {
//            // TODO: Заменить на реальный endpoint
//            val response = client.get("http://10.0.2.2:8080/api/v1/user/search?search=$search")
//            val users = response.body<List<UserMiniDto>>()
//            Result.success(users)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    override suspend fun updateUser(userUpdateDto: UserUpdateDto): Result<UserDto> {
//        return try {
//            // TODO: Заменить на реальный endpoint
//            val response = client.post("http://10.0.2.2:8080/api/v1/user") {
//                contentType(ContentType.Application.Json)
//                setBody(userUpdateDto)
//            }
//            val userDto = response.body<UserDto>()
//            Result.success(userDto)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}