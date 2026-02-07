package ru.sicampus.bootcamp2026.domain.usecase

import ru.sicampus.bootcamp2026.data.repository.AuthRepository
import ru.sicampus.bootcamp2026.domain.entities.User

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        secondName: String
    ): Result<Unit> {
        return authRepository.register(email, password, firstName, secondName).map { Unit }
    }
}