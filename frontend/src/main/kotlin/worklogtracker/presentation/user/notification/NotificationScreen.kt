package worklogtracker.presentation.user.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.text.HeaderText

@Composable
fun NotificationScreen(backStack: NavBackStack<NavKey>) {

    val notifications = listOf(
        NotificationUiState(
            title = "New favorite",
            message = "Someone added your car to favorites.",
            time = "2 min ago"
        ),
        NotificationUiState(
            title = "New order",
            message = "Your car has been booked successfully.",
            time = "1 hour ago"
        ),
        NotificationUiState(
            title = "Reminder",
            message = "Your rental ends tomorrow.",
            time = "Yesterday"
        )
    )

    RmcScreen (backStack = backStack) {
        Spacer(Modifier.height(20.dp))

        HeaderText("Notifications")

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification)
            }
        }
    }
}
