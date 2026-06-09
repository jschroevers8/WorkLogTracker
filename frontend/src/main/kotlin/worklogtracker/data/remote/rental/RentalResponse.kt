package worklogtracker.data.remote.rental

import kotlinx.serialization.Serializable

@Serializable
data class RentalResponse(
    val id: Int? = null,
    val userId: Int,
    val advertisementId: Int,
    val rentalStatus: RentalStatus,
    val pickUpDate: String,
    val returningDate: String,
    val rentalTrips: List<RentalTripResponse> = emptyList(),
)