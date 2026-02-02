package ru.sicampus.bootcamp2026.domain.usecase

import ru.sicampus.bootcamp2026.data.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.login(email, password)
    }
}