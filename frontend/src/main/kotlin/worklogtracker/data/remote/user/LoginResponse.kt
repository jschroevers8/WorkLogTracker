package worklogtracker.data.remote.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val error: String? = null,
    val token: String? = null,
)
