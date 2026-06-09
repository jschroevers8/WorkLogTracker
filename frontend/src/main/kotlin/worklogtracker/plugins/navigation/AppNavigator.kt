package worklogtracker.plugins.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import worklogtracker.data.local.AuthManager
import androidx.compose.material3.Text
import worklogtracker.presentation.user.account.AccountScreen
import worklogtracker.presentation.user.notification.NotificationScreen
import worklogtracker.presentation.user.login.LoginScreen
import worklogtracker.presentation.user.address.AddressScreen
import worklogtracker.presentation.user.signup.SignupScreen

@Composable
fun AppNavigator() {
    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }
    val backStack = rememberNavBackStack(Screen.Projects)

    val token by authManager.authTokenFlow.collectAsState(initial = null)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider@{ navKey ->
            val screen = navKey as? Screen
                ?: return@entryProvider NavEntry(navKey) { }
            NavEntry(screen) {

                if (screen.requiresAuth && token == null) {
                    LaunchedEffect(screen) {
                        backStack.add(Screen.Login(screen))
                    }

                    return@NavEntry
                }

                when (screen) {
                    is Screen.Login -> LoginScreen(backStack,screen.targetScreen)
                    is Screen.Signup -> SignupScreen(backStack, screen.targetScreen)
                    is Screen.Tasks -> Text("Tasks Screen")
                    is Screen.WorkLogs -> Text("WorkLogs Screen")
                    is Screen.Account -> AccountScreen(backStack)
                    is Screen.Address -> AddressScreen(backStack)
                    is Screen.Notification -> NotificationScreen(backStack)
                    else -> {}
                }
            }
        }
    )
}
