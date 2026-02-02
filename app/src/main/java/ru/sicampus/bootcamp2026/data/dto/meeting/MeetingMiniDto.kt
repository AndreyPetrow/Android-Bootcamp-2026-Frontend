package ru.sicampus.bootcamp2026.data.dto.meeting

import kotlinx.serialization.Serializable
import ru.sicampus.bootcamp2026.data.dto.user.UserMiniDto
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class MeetingMiniDto(
    val id: Long,
    val title: String,
    val description: String? = null,
    val address: String,
    val date: LocalDate,
    val timeStart: LocalDateTime,
    val timeEnd: LocalDateTime,
    val organizer: UserMiniDto
)