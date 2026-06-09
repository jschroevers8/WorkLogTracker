package worklogtracker.presentation.advertisement.create

import worklogtracker.data.remote.address.AddressResponse
import worklogtracker.data.remote.car.CarResponse
import worklogtracker.presentation.framework.annotations.Required
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class CreateAdvertisementUiState(
    @Required("Car must be selected")
    val carId: String = "",

    @Required("Address must be selected")
    val addressId: String = "",

    @Required("AvailableFrom field cannot be empty")
    val availableFrom: String = "",

    @Required("AvailableUntil field cannot be empty")
    val availableUntil: String = "",

    @Required("Price field cannot be empty")
    val price: String = "",

    val cars: List<CarResponse> = emptyList(),
    val addresses: List<AddressResponse> = emptyList(),

    override val loading: Boolean = false,
    override val error: String? = null
): BaseUiState

