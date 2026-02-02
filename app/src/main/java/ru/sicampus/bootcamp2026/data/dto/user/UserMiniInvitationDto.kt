package ru.sicampus.bootcamp2026.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserMiniInvitationDto(
    val id: Long,
    val firstName: String,
    val secondName: String,
    val photoUrl: String,
    val status: String,
    val respondedAt: String? = null
)