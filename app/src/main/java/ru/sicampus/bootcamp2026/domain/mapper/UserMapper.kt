package ru.sicampus.bootcamp2026.domain.mapper


import ru.sicampus.bootcamp2026.data.dto.user.UserDto
import ru.sicampus.bootcamp2026.data.dto.user.UserMiniDto
import ru.sicampus.bootcamp2026.data.dto.user.UserMiniInvitationDto
import ru.sicampus.bootcamp2026.data.dto.user.UserUpdateDto
import ru.sicampus.bootcamp2026.domain.entities.User
import ru.sicampus.bootcamp2026.domain.entities.UserMini
import ru.sicampus.bootcamp2026.domain.entities.UserMiniInvitation
import ru.sicampus.bootcamp2026.domain.entities.UserUpdate

object UserMapper {

    fun toDomain(dto: UserDto): User {
        return User(
            id = dto.id,
            firstName = dto.firstName,
            secondName = dto.secondName,
            description = dto.description,
            position = dto.position,
            department = dto.department,
            photoUrl = dto.photoUrl,
            role = dto.role,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }

    fun toDomain(dto: UserMiniDto): UserMini {
        return UserMini(
            id = dto.id,
            firstName = dto.firstName,
            secondName = dto.secondName,
            photoUrl = dto.photoUrl
        )
    }

    fun toDomain(dto: UserMiniInvitationDto): UserMiniInvitation {
        return UserMiniInvitation(
            id = dto.id,
            firstName = dto.firstName,
            secondName = dto.secondName,
            photoUrl = dto.photoUrl,
            status = dto.status,
            respondedAt = dto.respondedAt
        )
    }

    fun toDomain(dto: UserUpdateDto): UserUpdate {
        return UserUpdate(
            id = dto.id,
            firstName = dto.firstName,
            secondName = dto.secondName,
            description = dto.description,
            position = dto.position,
            department = dto.department
        )
    }

    fun toDto(domain: UserUpdate): UserUpdateDto {
        return UserUpdateDto(
            id = domain.id,
            firstName = domain.firstName,
            secondName = domain.secondName,
            description = domain.description,
            position = domain.position,
            department = domain.department
        )
    }

    fun toDto(domain: User): UserDto {
        return UserDto(
            id = domain.id,
            firstName = domain.firstName,
            secondName = domain.secondName,
            description = domain.description,
            position = domain.position,
            department = domain.department,
            photoUrl = domain.photoUrl ?: "",
            role = domain.role,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}