package ru.sicampus.bootcamp2026.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)