package worklogtracker.presentation.user.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.data.remote.user.UserType
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.framework.components.ConfirmDialog
import worklogtracker.presentation.framework.components.WltScreen
import worklogtracker.presentation.user.account.components.RequestRenterRollSection

@Composable
fun AccountScreenContent(
    viewModel: AccountViewModel,
    backStack: NavBackStack<NavKey>
) {
    val state = viewModel.uiState
    var showRequestRenterRollDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    viewModel.onLogoutSuccess = {
        backStack.add(Screen.Homepage)
    }

    LaunchedEffect(Unit) {
        viewModel.loadUser()
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
                    .background(Color(0xFF2C2C2C), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "${state.firstName} ${state.lastName}".trim().ifEmpty { "Loading..." },
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("AccountUserName")
            )

            Text(
                text = state.email,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.testTag("AccountUserEmail")
            )
        }

        Spacer(Modifier.height(32.dp))

        AccountRow("Add address","add_address" , Icons.Default.Edit) { backStack.add(Screen.Address) }

        RequestRenterRollSection(
            state = state,
            showDialog = showRequestRenterRollDialog,
            onShowDialog = { showRequestRenterRollDialog = true },
            onDismissDialog = { showRequestRenterRollDialog = false },
            onConfirm = {
                showRequestRenterRollDialog = false
                viewModel.requestRenterRoll()
            }
        )

        if (state.userType == UserType.ADMIN) {
            AccountRow("Renter aanvragen", "renter_request",Icons.Default.AdminPanelSettings) {
                backStack.add(Screen.AdminRenterRequests)
            }
        }

        AccountRow("My cars", "my_cars", Icons.Default.DirectionsCar) { backStack.add(Screen.PersonalCars) }
        AccountRow("My rentals", "my_rentals", Icons.Default.Receipt) { backStack.add(Screen.MyRentals) }
        AccountRow("Favorites", "favorites", Icons.Default.Favorite) { /* TODO */ }
        AccountRow("Settings", "settings", Icons.Default.Settings) { /* TODO */ }
        AccountRow("Help & support", "help_support", Icons.Default.Help) { /* TODO */ }

        Spacer(Modifier.weight(1f))

        AccountRow("Log out", "log_out", Icons.Default.Logout, iconTint = Color.Red) { showLogoutDialog = true }

        if (showLogoutDialog) {
            ConfirmDialog(
                title = "Uitloggen",
                message = "Weet je zeker dat je wilt uitloggen?",
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.logout()
                },
                onDismiss = { showLogoutDialog = false }
            )
        }

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = Color.Red)
        }

        Spacer(Modifier.height(16.dp))
    }
}
