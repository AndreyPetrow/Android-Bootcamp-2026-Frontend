package ru.sicampus.bootcamp2026.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingDto
import ru.sicampus.bootcamp2026.data.dto.meeting.MeetingMiniDto
import ru.sicampus.bootcamp2026.data.source.ApiClient
import ru.sicampus.bootcamp2026.data.source.ApiClientImpl
import java.time.LocalDate

interface MeetingRepository {
    suspend fun createMeeting(meetingData: Map<String, Any>): Result<MeetingDto>
    suspend fun getMeetingById(id: Long): Result<MeetingDto>
    suspend fun getDaySchedule(day: LocalDate): Result<List<MeetingMiniDto>>
    suspend fun getWeekSchedule(year: Int, week: Int): Result<List<MeetingMiniDto>>
    suspend fun getMonthSchedule(year: Int, month: Int): Result<List<MeetingMiniDto>>
    suspend fun getInvitations(status: String = "pending"): Result<List<MeetingMiniDto>>
    suspend fun respondToInvitation(meetingId: Long, response: Boolean): Result<Unit>
}

class MeetingRepositoryImpl(
    private val client: HttpClient = ApiClientImpl().client
) : MeetingRepository {

    override suspend fun createMeeting(meetingData: Map<String, Any>): Result<MeetingDto> {
        return try {
            // TODO: Заменить на реальный endpoint
            val response = client.post("http://10.0.2.2:8080/api/v1/meeting/create") {
                contentType(ContentType.Application.Json)
                setBody(meetingData)
            }
            val meetingDto = response.body<MeetingDto>()
            Result.success(meetingDto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMeetingById(id: Long): Result<MeetingDto> {
        return try {
            val response = client.get("http://10.0.2.2:8080/api/v1/meeting/$id")
            val meetingDto = response.body<MeetingDto>()
            Result.success(meetingDto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDaySchedule(day: LocalDate): Result<List<MeetingMiniDto>> {
        return try {
            val response = client.get("http://10.0.2.2:8080/api/v1/meeting/schedule/day?day=$day")
            val meetings = response.body<List<MeetingMiniDto>>()
            Result.success(meetings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWeekSchedule(year: Int, week: Int): Result<List<MeetingMiniDto>> {
        return try {
            val response = client.get("http://10.0.2.2:8080/api/v1/meeting/schedule/week?year=$year&week=$week")
            val meetings = response.body<List<MeetingMiniDto>>()
            Result.success(meetings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonthSchedule(year: Int, month: Int): Result<List<MeetingMiniDto>> {
        return try {
            val response = client.get("http://10.0.2.2:8080/api/v1/meeting/schedule/month?year=$year&month=$month")
            val meetings = response.body<List<MeetingMiniDto>>()
            Result.success(meetings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInvitations(status: String): Result<List<MeetingMiniDto>> {
        return try {
            // TODO: Заменить на реальный endpoint (возможно из контроллера приглашений)
            val response = client.get("http://10.0.2.2:8080/api/v1/meeting/invitations?status=$status")
            val invitations = response.body<List<MeetingMiniDto>>()
            Result.success(invitations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun respondToInvitation(meetingId: Long, response: Boolean): Result<Unit> {
        return try {
            // TODO: Заменить на реальный endpoint
            client.post("http://10.0.2.2:8080/api/v1/meeting/$meetingId/respond") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("response" to response))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
