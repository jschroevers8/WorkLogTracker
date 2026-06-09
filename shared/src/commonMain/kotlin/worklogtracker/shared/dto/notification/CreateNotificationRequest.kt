package worklogtracker.shared.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class CreateNotificationRequest(
    val userId: String,
    val title: String,
    val message: String,
    val type: String,
    val taskId: String? = null
)
