package worklogtracker.frontend.presentation.user.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.frontend.presentation.framework.components.ConfirmDialog
import worklogtracker.frontend.presentation.framework.components.WltScreen
import worklogtracker.frontend.presentation.framework.theme.WltColors

@Composable
fun AccountScreenContent(
    state: AccountUiState,
    onLoadUser: () -> Unit,
    onLogout: () -> Unit,
    backStack: NavBackStack<NavKey>
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onLoadUser()
    }

    WltScreen(backStack = backStack) {
        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(WltColors.Surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = WltColors.Primary,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "${state.firstName} ${state.lastName}".trim().ifEmpty { "Loading..." },
                color = WltColors.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("AccountUserName")
            )

            Text(
                text = state.email,
                color = WltColors.TextSecondary,
                fontSize = 14.sp,
                modifier = Modifier.testTag("AccountUserEmail")
            )

            Spacer(Modifier.height(8.dp))

            Surface(
                color = WltColors.Primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Worklog Tracker Profile",
                    color = WltColors.Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        AccountRow("Log out", "log_out", Icons.Default.Logout, iconTint = WltColors.Error) { showLogoutDialog = true }

        if (showLogoutDialog) {
            ConfirmDialog(
                title = "Uitloggen",
                message = "Weet je zeker dat je wilt uitloggen?",
                onConfirm = {
                    showLogoutDialog = false
                    onLogout()
                },
                onDismiss = { showLogoutDialog = false }
            )
        }

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = WltColors.Error)
        }

        Spacer(Modifier.height(16.dp))
    }
}
