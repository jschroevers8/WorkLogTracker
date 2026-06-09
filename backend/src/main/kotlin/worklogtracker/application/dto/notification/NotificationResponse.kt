package worklogtracker.application.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val id: Int?,
    val userId: Int,
    val taskId: Int?,
    val title: String,
    val message: String,
    val type: String,
    val sentAt: String,
    val isRead: Boolean,
    val createdAt: String
)
