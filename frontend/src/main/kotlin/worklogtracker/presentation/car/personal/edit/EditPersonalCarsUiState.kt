package worklogtracker.presentation.car.personal.edit

import worklogtracker.data.remote.car.BodyType
import worklogtracker.data.remote.car.FuelType
import worklogtracker.presentation.framework.annotations.Required
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class EditPersonalCarsUiState(
    @Required("Brand field cannot be empty")
    val brand: String = "",

    @Required("Model field cannot be empty")
    val model: String = "",

    @Required("Model year field cannot be empty")
    val modelYear: String = "",

    @Required("License plate field cannot be empty")
    val licensePlate: String = "",

    @Required("Mileage field cannot be empty")
    val mileage: String = "",

    val fuelType: FuelType = FuelType.ICE,
    val bodyType: BodyType = BodyType.SEDAN,
    val carImages: List<String> = emptyList(),

    override val loading: Boolean = false,
    override val error: String? = null
): BaseUiState
