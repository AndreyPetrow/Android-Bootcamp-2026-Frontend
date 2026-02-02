package ru.sicampus.bootcamp2026.data.dto.meeting

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MeetingCreate(
    val title: String,
    val description: String? = null,
    val address: String,
    val date: String,
    val timeStart: LocalDateTime,//это на беке лучше наверное
    val timeEnd: LocalDateTime,
    val organizerId: Long,
    val participantIds: List<Long> = emptyList()
)