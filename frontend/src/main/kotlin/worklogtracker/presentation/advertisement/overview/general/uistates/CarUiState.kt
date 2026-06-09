package worklogtracker.presentation.advertisement.overview.general.uistates

data class CarUiState(
    val id: Int? = null,
    val fuelType: String,
    val userId: Int,
    val bodyType: String,
    val brand: String,
    val model: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val createdStamp: String,
)
