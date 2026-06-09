package worklogtracker.data.remote.rental

import kotlinx.serialization.Serializable

@Serializable
data class CreateRental(
    val advertisementId: Int,
    val pickUpDate: String,
    val returningDate: String,
)
