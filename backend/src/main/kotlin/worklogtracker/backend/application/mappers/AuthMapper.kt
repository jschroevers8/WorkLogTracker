package worklogtracker.backend.application.mappers

import worklogtracker.backend.domain.entities.UserEntity
import worklogtracker.shared.dto.auth.AuthResponse

fun UserEntity.toResponse(token: String) =
    AuthResponse(
        userId = id?.value,
        email = email.value,
        firstName = firstName,
        lastName = lastName,
        role = role.name,
        token = token,
    )
