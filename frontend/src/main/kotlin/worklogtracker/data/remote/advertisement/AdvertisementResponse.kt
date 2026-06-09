package worklogtracker.data.remote.advertisement

import kotlinx.serialization.Serializable
import worklogtracker.data.remote.address.AddressResponse
import worklogtracker.data.remote.car.CarResponse

@Serializable
data class AdvertisementResponse(
    val id: Int,
    val availableFrom: String,
    val availableUntil: String,
    val price: Double,
    val address: AddressResponse,
    val car: CarResponse,
)
