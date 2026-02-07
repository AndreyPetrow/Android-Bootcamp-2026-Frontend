package ru.sicampus.bootcamp2026.data.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserRegisterDto(
    @SerialName("email")  val email: String,
    @SerialName("email")  val password: String,
    @SerialName("email")  val firstName: String,
    @SerialName("email")  val secondName: String
)