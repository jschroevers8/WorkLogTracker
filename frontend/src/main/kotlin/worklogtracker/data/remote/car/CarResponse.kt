package worklogtracker.data.remote.car

import kotlinx.serialization.Serializable
import worklogtracker.data.remote.car.CarImageResponse

@Serializable
data class CarResponse(
    val id: Int? = null,
    val fuelType: FuelType,
    val userId: Int,
    val bodyType: BodyType,
    val brand: String,
    val model: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val createdStamp: String,
    val carImages: List<CarImageResponse> = emptyList(),
)
