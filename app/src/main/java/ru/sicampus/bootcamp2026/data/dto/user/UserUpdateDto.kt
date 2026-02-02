package ru.sicampus.bootcamp2026.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateDto(
    val id: Long,
    val firstName: String,
    val secondName: String,
    val description: String? = null,
    val position: String? = null,
    val department: String? = null
)