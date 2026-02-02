package ru.sicampus.bootcamp2026.data.dto.meeting

import kotlinx.serialization.Serializable
import ru.sicampus.bootcamp2026.data.dto.user.UserMiniDto
import ru.sicampus.bootcamp2026.data.dto.user.UserMiniInvitationDto
import java.time.LocalDateTime

@Serializable
data class MeetingDto(
    val id: Long,
    val title: String,
    val address: String,
    val description: String? = null,
    val date: String,
    val timeStart: LocalDateTime,
    val timeEnd: LocalDateTime,
    val organizer: UserMiniDto,
    val users: List<UserMiniInvitationDto> = emptyList(),
    val createAt: LocalDateTime
)