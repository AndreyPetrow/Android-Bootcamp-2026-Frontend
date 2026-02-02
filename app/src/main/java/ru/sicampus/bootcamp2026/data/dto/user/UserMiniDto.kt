package ru.sicampus.bootcamp2026.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserMiniDto(
    val id: Long,
    val firstName: String,
    val secondName: String,
    val photoUrl: String
)