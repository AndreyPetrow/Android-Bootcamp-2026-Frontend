package ru.sicampus.bootcamp2026.ui.screens.profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.sicampus.bootcamp2026.data.repository.UserRepository
import ru.sicampus.bootcamp2026.data.source.UserDataSource
import ru.sicampus.bootcamp2026.domain.usecase.user.GetUserByIdUseCase
import ru.sicampus.bootcamp2026.domain.usecase.user.SearchUserUseCase
import ru.sicampus.bootcamp2026.domain.usecase.user.UserUpdateUseCase

object ProfileViewModelFactory {
    fun create(): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val userDataSource = UserDataSource()
            val userRepository = UserRepository(userDataSource)
            val getUserByIdUseCase = GetUserByIdUseCase(userRepository)
            val updateUseCase = UserUpdateUseCase(userRepository)
            val searchUserUseCase = SearchUserUseCase(userRepository)

            ProfileViewModel(getUserByIdUseCase, updateUseCase, searchUserUseCase)
        }
    }
}