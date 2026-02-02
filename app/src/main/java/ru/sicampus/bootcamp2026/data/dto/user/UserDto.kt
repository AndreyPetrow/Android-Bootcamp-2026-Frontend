package ru.sicampus.bootcamp2026.data.dto.user

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserDto(
    val id: Long,
    val firstName: String,
    val secondName: String,
    val description: String? = null,
    val position: String? = null,
    val department: String? = null,
    val photoUrl: String,
    val role: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)