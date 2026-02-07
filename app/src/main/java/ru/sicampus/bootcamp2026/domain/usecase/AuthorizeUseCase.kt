package ru.sicampus.bootcamp2026.domain.usecase

import ru.sicampus.bootcamp2026.data.repository.AuthRepository

class AuthorizeUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Boolean> {
        return authRepository.checkAndAuth(email, password)
    }
}