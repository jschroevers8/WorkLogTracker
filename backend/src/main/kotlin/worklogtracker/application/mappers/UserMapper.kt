package worklogtracker.application.mappers

import worklogtracker.domain.entities.UserEntity
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.user.UserResponse

fun UserEntity.toResponse(token: String) =
    AuthResponse(
        userId = id?.value,
        email = email.value,
        firstName = firstName,
        lastName = lastName,
        role = role.name,
        token = token
    )

fun UserEntity.toUserResponse() =
    UserResponse(
        id = id?.value?.toLong() ?: 0L,
        email = email.value,
        firstName = firstName,
        lastName = lastName,
        role = role.name
    )
