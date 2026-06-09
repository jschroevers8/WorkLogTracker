package worklogtracker.presentation.user.notification

import worklogtracker.presentation.framework.viewmodel.BaseViewModel

class NotificationViewModel : BaseViewModel<NotificationListUiState>(NotificationListUiState()) {

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        launchWithErrorHandling {
            // Mock data for now
            val mockNotifications = listOf(
                NotificationItemUiState(
                    title = "New favorite",
                    message = "Someone added your car to favorites.",
                    time = "2 min ago"
                ),
                NotificationItemUiState(
                    title = "New order",
                    message = "Your car has been booked successfully.",
                    time = "1 hour ago"
                ),
                NotificationItemUiState(
                    title = "Reminder",
                    message = "Your rental ends tomorrow.",
                    time = "Yesterday"
                )
            )
            _uiState = uiState.copy(notifications = mockNotifications)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
