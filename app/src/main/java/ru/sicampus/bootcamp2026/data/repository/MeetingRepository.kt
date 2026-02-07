package ru.sicampus.bootcamp2026.data.repository

import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingCreateDto
import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingDto
import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingMiniDto
import ru.sicampus.bootcamp2026.data.source.ApiClient
import ru.sicampus.bootcamp2026.data.source.DataStoreManager
import java.time.LocalDate

interface MeetingRepository {
    suspend fun createMeeting(meetingData: MeetingCreateDto): Result<MeetingDto>
    suspend fun getMeetingById(id: Long): Result<MeetingDto>
    suspend fun getDaySchedule(day: LocalDate): Result<List<MeetingMiniDto>>
    suspend fun getWeekSchedule(year: Int, week: Int): Result<Map<String, List<MeetingMiniDto>>>
    suspend fun getMonthSchedule(year: Int, month: Int): Result<Map<String, List<MeetingMiniDto>>>
    suspend fun getInvitations(): Result<List<ru.sicampus.bootcamp2026.data.dto.invitation.InvitationDto>>
    suspend fun respondToInvitation(invitationId: Long, status: String): Result<Unit>
}

class MeetingRepositoryImpl : MeetingRepository {

    override suspend fun createMeeting(meetingData: MeetingCreateDto): Result<MeetingDto> {
        return try {
            ApiClient.post<MeetingDto, MeetingCreateDto>("meetings/create", meetingData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMeetingById(id: Long): Result<MeetingDto> {
        return try {
            ApiClient.get("meetings/$id")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDaySchedule(day: LocalDate): Result<List<MeetingMiniDto>> {
        return try {
            val params = mapOf("day" to day.toString())
            ApiClient.getWithParams("meetings/schedule/day", params)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWeekSchedule(year: Int, week: Int): Result<Map<String, List<MeetingMiniDto>>> {
        return try {
            val params = mapOf(
                "year" to year.toString(),
                "week" to week.toString()
            )
            ApiClient.getWithParams("meetings/schedule/week", params)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonthSchedule(year: Int, month: Int): Result<Map<String, List<MeetingMiniDto>>> {
        return try {
            val params = mapOf(
                "year" to year.toString(),
                "month" to month.toString()
            )
            ApiClient.getWithParams("meetings/schedule/month", params)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInvitations(): Result<List<ru.sicampus.bootcamp2026.data.dto.invitation.InvitationDto>> {
        return try {
            ApiClient.get("invitation")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun respondToInvitation(invitationId: Long, status: String): Result<Unit> {
        return try {
            val body = mapOf(
                "invitationId" to invitationId,
                "status" to status
            )
            ApiClient.put<Unit, Map<String, Any>>("invitation/respond", body)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}