package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class RecordTaskLocationRequest(
    val taskId: Int,
    val latitude: Double,
    val longitude: Double
)
