package worklogtracker.data.remote.user

import kotlinx.serialization.Serializable

@Serializable
data class CreateAddressRequest(
    val city: String,
    val street: String,
    val houseNumber: Int,
    val subHouseNumber: String? = null,
    val postalCode: String,
)