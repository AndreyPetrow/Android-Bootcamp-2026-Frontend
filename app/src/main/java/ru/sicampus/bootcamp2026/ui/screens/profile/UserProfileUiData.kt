package ru.sicampus.bootcamp2026.ui.screens.profile

data class UserProfileUiData(
    val fullName: String = "",
    val email: String = "",
    val position: String? = null,
    val department: String? = null,
    val description: String? = null,
    val photoUrl: String = "",

    val updateFirstName: String = "",
    val updateSecondName: String = "",
    val updatePosition:  String? = null,
    val updateDepartment:  String? = null,
    val updateDescription:  String? = null,

    val errorMessage: String? = null
)