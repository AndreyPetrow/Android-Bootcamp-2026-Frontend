package ru.sicampus.bootcamp2026.data.dto.meeting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class MeetingCreateDto(
    @SerialName("title") val title: String,
    @SerialName("address") val address: String,
    @SerialName("description") val description: String? = null,
    @SerialName("date") val date: LocalDate,
    @SerialName("timeStart") val timeStart: LocalTime,
    @SerialName("timeEnd") val timeEnd: LocalTime,
    @SerialName("usersId") val usersId: List<Long> = emptyList()
)