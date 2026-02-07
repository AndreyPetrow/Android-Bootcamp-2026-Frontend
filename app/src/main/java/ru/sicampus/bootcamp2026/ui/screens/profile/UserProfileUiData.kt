package ru.sicampus.bootcamp2026.ui.screens.profile

data class UserProfileUiData(
    val fullName: String = "",
    val email: String = "",
    val position: String? = null,
    val department: String? = null,
    val description: String? = null,
    val photoUrl: String = "",

    val errorMessage: String? = null
)