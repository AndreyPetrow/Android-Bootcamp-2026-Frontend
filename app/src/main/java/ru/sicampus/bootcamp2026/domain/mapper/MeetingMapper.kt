package ru.sicampus.bootcamp2026.domain.mapper

import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingCreate
import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingDto
import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingMiniDto
import ru.sicampus.bootcamp2026.domain.entities.Meeting
import ru.sicampus.bootcamp2026.domain.entities.MeetingMini

object MeetingMapper {

    fun toDomain(dto: MeetingDto): Meeting {
        return Meeting(
            id = dto.id,
            title = dto.title,
            address = dto.address,
            description = dto.description,
            date = dto.date,
            timeStart = dto.timeStart,
            timeEnd = dto.timeEnd,
            organizer = UserMapper.toDomain(dto.organizer),
            participants = dto.users.map { UserMapper.toDomain(it) },
            createdAt = dto.createAt
        )
    }

    fun toDomain(dto: MeetingMiniDto): MeetingMini {
        return MeetingMini(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            address = dto.address,
            date = dto.date,
            timeStart = dto.timeStart,
            timeEnd = dto.timeEnd,
            organizer = UserMapper.toDomain(dto.organizer)
        )
    }
}