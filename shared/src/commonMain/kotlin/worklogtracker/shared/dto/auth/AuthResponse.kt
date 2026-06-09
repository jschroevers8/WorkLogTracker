package worklogtracker.shared.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val userId: Int?,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val token: String
)
