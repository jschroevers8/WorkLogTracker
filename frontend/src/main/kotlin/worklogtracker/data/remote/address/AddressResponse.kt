package worklogtracker.data.remote.address

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val id: Int,
    val city: String,
    val street: String,
    val houseNumber: Int,
    val subHouseNumber: String?,
    val postalCode: String,
)


fun AddressResponse.toSingleLine(): String =
    "$street $houseNumber, $postalCode $city Nederland"
