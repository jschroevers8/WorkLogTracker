package worklogtracker.frontend.presentation.user.notification.item

data class NotificationItem(
    val id: Int,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
)
