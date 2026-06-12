package worklogtracker.backend.application.mappers

import worklogtracker.backend.domain.entities.UserEntity
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.user.UserResponse

fun UserEntity.toUserResponse() =
    UserResponse(
        id = id?.value?.toLong() ?: 0L,
        email = email.value,
        firstName = firstName,
        lastName = lastName,
        role = role.name
    )
