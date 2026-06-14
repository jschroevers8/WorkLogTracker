package worklogtracker.shared.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)
