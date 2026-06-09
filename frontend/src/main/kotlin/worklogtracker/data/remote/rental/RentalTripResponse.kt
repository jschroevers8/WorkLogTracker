package worklogtracker.data.remote.rental

import kotlinx.serialization.Serializable

@Serializable
data class RentalTripResponse(
    val id: Int? = null,
    val rentalId: Int,
    val startMileage: Int,
    val endMileage: Int,
    val startDate: String,
    val endDate: String,
)
