package ru.sicampus.bootcamp2026.domain.usecase

import ru.sicampus.bootcamp2026.data.dto.user.UserDto
import ru.sicampus.bootcamp2026.data.repository.UserRepositoryImpl
import ru.sicampus.bootcamp2026.domain.entities.User
import ru.sicampus.bootcamp2026.domain.mapper.UserMapper
import java.time.LocalDateTime

class GetUserUseCase(
    private val userRepository: UserRepositoryImpl,
) {
    suspend operator fun invoke(userId: Long): Result<User> {
        return userRepository.getUserById(userId).mapCatching { response ->
            UserMapper.toDomain(response)
        }
    }
}