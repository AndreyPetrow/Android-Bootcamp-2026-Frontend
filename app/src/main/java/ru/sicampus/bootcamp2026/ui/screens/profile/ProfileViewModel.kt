package ru.sicampus.bootcamp2026.ui.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.App
import ru.sicampus.bootcamp2026.data.repository.UserRepositoryImpl
import ru.sicampus.bootcamp2026.domain.usecase.GetUserUseCase
import ru.sicampus.bootcamp2026.utils.SettingsUtils


class ProfileViewModel() : ViewModel() {
    val settingsUtils = SettingsUtils(App.context)

    private val userId by lazy { settingsUtils.getUserId() }

    private val _uiState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    private val _navigationEvents: Channel<ActionState> = Channel()
    val navigationEvents: Flow<ActionState> = _navigationEvents.receiveAsFlow()

    val getUserUseCase by lazy { GetUserUseCase(UserRepositoryImpl()) }

    val errorText = mutableStateOf("")

    init { update() }

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.Request -> {
                viewModelScope.launch {
                    _uiState.update { ProfileState.Loading }

                    getUserUseCase.invoke(userId).fold(
                        onSuccess = { user ->
                            errorText.value = ""
                            _uiState.value = ProfileState.Data(
                                fullName = user.firstName + user.secondName,
                                email = user.email,
                                description = user.description,
                                position = user.position,
                                department = user.department,
                                photoUrl = user.photoUrl,
                                createdAt = user.createdAt,
                            )
                        },
                        onFailure = { error ->
                            _uiState.value = ProfileState.Error(error.message.toString())
                        }
                    )
                }
            }

            ProfileIntent.Logout -> TODO()
            ProfileIntent.Update -> TODO()
        }
    }

    fun update() {
        onIntent(ProfileIntent.Request)
    }

    fun navigate(actionState: ActionState) {
        viewModelScope.launch {
            _navigationEvents.send(actionState)
        }
    }

//
//    init {
//        loadUserData()
//    }
//
//    private fun loadUserData() {
//        //пока так
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            _state.value = _state.value.copy(
//                user = UserDto(
//                    id = 1,
//                    firstName = "Алексей",
//                    secondName = "Петров",
//                    description = "Я — великий Frontend-разработчик. Моя главная задача — превращать идеи дизайнеров и требования бизнеса в быстрые, удобные и красивые интерфейсы спомощью котлина.",
//                    position = "Frontend-Разработчик",
//                    department = "Разработка продуктов",
//                    photoUrl = "",
//                    role = "ROLE_USER",
//                    createdAt = LocalDateTime.now(),
//                    updatedAt = LocalDateTime.now()
//                ),
//                editData = UserUpdateDto(
//                    id = 1,
//                    firstName = "Алексей",
//                    secondName = "Петров",
//                    description = "Я — Frontend-разработчик. Моя главная задача — превращать идеи дизайнеров и требования бизнеса в быстрые, удобные и красивые интерфейсы.",
//                    position = "Frontend-Разработчик",
//                    department = "Разработка продуктов"
//                )
//            )
//        }
//    }
//
//    fun onEditClick() {
//        _state.value = _state.value.copy(isEditing = true)
//    }
//
//    fun onCancelEdit() {
//        _state.value = _state.value.copy(isEditing = false)
//    }
//
//    fun onSaveProfile(updatedData: UserUpdateDto) {
//        viewModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true, error = null)
//
//            val result = updateProfileUseCase(updatedData)
//
//            result.fold(
//                onSuccess = {
//                    _state.value = _state.value.copy(
//                        isLoading = false,
//                        isEditing = false,
//                        user = _state.value.user?.copy(
//                            firstName = updatedData.firstName,
//                            secondName = updatedData.secondName,
//                            description = updatedData.description,
//                            position = updatedData.position,
//                            department = updatedData.department
//                        )
//                    )
//                },
//                onFailure = { error ->
//                    _state.value = _state.value.copy(
//                        isLoading = false,
//                        error = error.message ?: "Ошибка обновления профиля"
//                    )
//                }
//            )
//        }
//    }
//
//    fun updateEditData(newData: UserUpdateDto) {
//        _state.value = _state.value.copy(editData = newData)
//    }
//
//    fun clearError() {
//        _state.value = _state.value.copy(error = null)
//    }
}