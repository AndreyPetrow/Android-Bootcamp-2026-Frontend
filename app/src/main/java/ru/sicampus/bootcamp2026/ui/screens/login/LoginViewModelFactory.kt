package ru.sicampus.bootcamp2026.ui.screens.login

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.sicampus.bootcamp2026.data.repository.AuthRepository
import ru.sicampus.bootcamp2026.data.source.dataSource.AuthDataSource
import ru.sicampus.bootcamp2026.domain.usecase.LoginUseCase

object LoginViewModelFactory {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val authDataSource = AuthDataSource()
            val authRepository = AuthRepository(authDataSource)
            val loginUseCase = LoginUseCase(authRepository)

            LoginViewModel(loginUseCase)
        }
    }
}