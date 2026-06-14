package worklogtracker.frontend.presentation.user.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.frontend.presentation.framework.components.WltScreen
import worklogtracker.frontend.presentation.framework.components.text.HeaderText

import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: NotificationViewModel = koinViewModel()
) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
    }

    WltScreen (backStack = backStack) {
        Spacer(Modifier.height(20.dp))

        HeaderText("Notifications")

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(state.notifications) { notification ->
                NotificationCard(notification) {
                    viewModel.markAsRead(notification.id)
                }
            }
        }
    }
}
