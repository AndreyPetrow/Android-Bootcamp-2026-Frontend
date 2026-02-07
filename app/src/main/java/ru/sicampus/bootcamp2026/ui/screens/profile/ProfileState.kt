package ru.sicampus.bootcamp2026.ui.screens.profile

import java.time.LocalDateTime

sealed interface ProfileState {

    object Loading: ProfileState

    data class Data(
        val fullName: String,
        val email: String,
        val description: String?,
        val position: String?,
        val department: String?,
        val photoUrl: String,
        val createdAt: LocalDateTime,
    ) : ProfileState

    data class Error(
        val message: String,
    ) : ProfileState

    object EditData : ProfileState



}