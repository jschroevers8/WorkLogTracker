package worklogtracker.data.remote.car

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class CreateCar(
    val brand: String,
    val model: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val fuelType: FuelType,
    val bodyType: BodyType,
    val createdStamp: String,
    val carImages: List<CreateCarImage>
)
