package worklogtracker.data.remote.user

import kotlinx.serialization.Serializable
import worklogtracker.data.remote.address.AddressResponse

@Serializable
data class UserResponse(
    val id: Int,
    val userType: UserType,
    val addresses: List<AddressResponse> = emptyList(),
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val userPoints: Int,
    val renterRightsRequested: Boolean,
)
