package worklogtracker.frontend.presentation.user.notification

import worklogtracker.frontend.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.frontend.repositories.NotificationRepository
import worklogtracker.shared.dto.notification.NotificationResponse

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : BaseViewModel<NotificationListUiState>(NotificationListUiState()) {
    fun loadNotifications() {
        launchWithErrorHandling {
            val notifications = notificationRepository.getNotifications()
            _uiState = uiState.copy(
                notifications = notifications.map { it.toUiState() }.sortedByDescending { it.time }
            )
        }
    }

    fun markAsRead(id: Int) {
        launchWithErrorHandling {
            notificationRepository.markAsRead(id)
            // Update local state instead of full reload for better UX
            _uiState = uiState.copy(
                notifications = uiState.notifications.map {
                    if (it.id == id) it.copy(isRead = true) else it
                }
            )
        }
    }

    private fun NotificationResponse.toUiState(): NotificationItemUiState {
        return NotificationItemUiState(
            id = id ?: 0,
            title = title,
            message = message,
            time = createdAt.replace("T", " "), // Simple formatting
            isRead = isRead
        )
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
