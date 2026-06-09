package worklogtracker.data.remote.car

import kotlinx.serialization.Serializable

@Serializable
data class CarImageResponse(
    val id: Int? = null,
    val image: String,
    val weight: Int,
)