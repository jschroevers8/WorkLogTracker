package worklogtracker.application.mappers

import worklogtracker.application.dto.auth.AuthResponse
import worklogtracker.domain.entities.UserEntity

fun UserEntity.toResponse(token: String) =
    AuthResponse(
        userId = id?.value,
        email = email.value,
        firstName = firstName,
        lastName = lastName,
        role = role.name,
        token = token
    )
