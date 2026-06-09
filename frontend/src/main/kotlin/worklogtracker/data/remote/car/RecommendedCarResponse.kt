package worklogtracker.data.remote.car

import kotlinx.serialization.Serializable

@Serializable
data class RecommendedCarResponse(
    val message: String,
    val carId: Int? = null
)
