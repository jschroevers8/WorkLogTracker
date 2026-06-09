package worklogtracker.data.remote.address

import kotlinx.serialization.Serializable

@Serializable
data class CreateAddress(
    val city: String,
    val street: String,
    val houseNumber: Int,
    val subHouseNumber: String?,
    val postalCode: String,
)
