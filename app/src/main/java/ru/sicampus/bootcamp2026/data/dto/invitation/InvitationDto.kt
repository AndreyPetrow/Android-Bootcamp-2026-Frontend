package ru.sicampus.bootcamp2026.data.dto.invitation;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable;
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class InvitationDto(
    @SerialName("id") val id: Long,
    @SerialName("long") val authorId: Long,
    @SerialName("id") val authorFirstName: String,
    @SerialName("id") val authorSecondName: String,
    @SerialName("id") val meetingId: Long,
    @SerialName("id") val title: String,
    @SerialName("id") val address: String,
    @SerialName("id") val date: LocalDate,
    @SerialName("id") val timeStart: LocalTime,
    @SerialName("id") val timeEnd: LocalTime
)