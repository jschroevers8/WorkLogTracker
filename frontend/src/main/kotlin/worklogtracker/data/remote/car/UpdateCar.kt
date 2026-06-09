package worklogtracker.data.remote.car

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCar(
    val id: Int? = null,
    val fuelType: FuelType,
    val bodyType: BodyType,
    val brand: String,
    val model: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val createdStamp: String,
    val carImages: List<CreateCarImage> = emptyList(),
)
