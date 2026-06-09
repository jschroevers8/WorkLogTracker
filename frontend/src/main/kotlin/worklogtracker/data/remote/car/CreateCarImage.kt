package worklogtracker.data.remote.car

import kotlinx.serialization.Serializable

@Serializable
data class CreateCarImage(
    val image: String,
)
