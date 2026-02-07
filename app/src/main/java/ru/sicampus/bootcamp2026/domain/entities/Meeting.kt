package ru.sicampus.bootcamp2026.domain.entities

import java.time.LocalDate
import java.time.LocalDateTime

data class Meeting(
    val id: Long,
    val title: String,
    val address: String,
    val description: String? = null,
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val organizer: UserMini,
    val users: List<UserMiniInvitation> = emptyList(),
    val createdAt: String
)

data class MeetingMini(
    val id: Long,
    val title: String,
    val description: String? = null,
    val address: String,
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val organizer: UserMini
)

data class MeetingCreate(
    val title: String,
    val description: String? = null,
    val address: String,
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val userId: List<Long> = emptyList()
)