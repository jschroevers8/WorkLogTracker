package worklogtracker.data.remote.user

import kotlinx.serialization.Serializable
import worklogtracker.data.remote.address.CreateAddress

@Serializable
data class CreateUser(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
)
