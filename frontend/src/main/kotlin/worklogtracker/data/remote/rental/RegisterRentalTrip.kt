package worklogtracker.data.remote.rental

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRentalTrip(
    val rentalId: Int,
    val startMileage: Int,
    val endMileage: Int,
    val startDate: String,
    val endDate: String,
)
