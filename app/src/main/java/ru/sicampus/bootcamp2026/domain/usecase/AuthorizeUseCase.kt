package ru.sicampus.bootcamp2026.domain.usecase

import ru.sicampus.bootcamp2026.data.repository.AuthRepository

class AuthorizeUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.validateSession().map{Unit}
    }
}