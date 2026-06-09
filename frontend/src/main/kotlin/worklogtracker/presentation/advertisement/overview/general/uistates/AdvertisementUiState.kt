package worklogtracker.presentation.advertisement.overview.general.uistates

import worklogtracker.data.remote.address.AddressResponse
import worklogtracker.data.remote.car.CarResponse

data class AdvertisementUiState(
    val id: Int,
    val availableFrom: String,
    val availableUntil: String,
    val price: String,
    val address: AddressResponse,
    val car: CarResponse
)