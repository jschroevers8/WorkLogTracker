package worklogtracker.presentation.user.notification

import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class NotificationListUiState(
    val notifications: List<NotificationItemUiState> = emptyList(),
    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState

data class NotificationItemUiState(
    val title: String,
    val message: String,
    val time: String
)
