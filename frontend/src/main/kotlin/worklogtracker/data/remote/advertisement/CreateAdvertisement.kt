package worklogtracker.data.remote.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class CreateAdvertisement(
    val carId: Int,
    val addressId: Int,
    val availableFrom: String,
    val availableUntil: String,
    val price: Double
)
