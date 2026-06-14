package worklogtracker.frontend.presentation.user.notification

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState
import worklogtracker.frontend.presentation.user.notification.item.NotificationItem

data class NotificationsUiState(
    val notifications: List<NotificationItem> = emptyList(),
    override val loading: Boolean = false,
    override val error: String? = null,
) : BaseUiState
