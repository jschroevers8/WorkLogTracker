package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class TaskLocation(
    val latitude: Double,
    val longitude: Double,
    val recordedAt: String,
)
