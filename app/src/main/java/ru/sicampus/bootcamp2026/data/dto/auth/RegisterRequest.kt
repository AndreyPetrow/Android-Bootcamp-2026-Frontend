package ru.sicampus.bootcamp2026.data.dto.auth

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val secondName: String
)