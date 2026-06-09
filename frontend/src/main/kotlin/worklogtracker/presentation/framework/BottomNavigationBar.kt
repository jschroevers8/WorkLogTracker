package worklogtracker.presentation.framework

import worklogtracker.plugins.navigation.Screen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.compose.koinInject
import worklogtracker.data.local.AuthManagerInterface
import worklogtracker.data.remote.user.UserType
import worklogtracker.repositories.UserRepository

@Composable
fun BottomNavigationBar(
    backStack: NavBackStack<NavKey>,
    authManager: AuthManagerInterface = koinInject(),
    userRepository: UserRepository = koinInject(),
    onItemSelected: (Screen) -> Unit
) {
    val token by authManager.authTokenFlow.collectAsState(initial = null)

    val userType by produceState<UserType?>(initialValue = null, key1 = token) {
        value = if (token != null) {
            try {
                userRepository.getUser().userType
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    val items = mutableListOf(
        Screen.Projects to Icons.Default.Folder,
        Screen.Tasks to Icons.Default.Assignment,
        Screen.WorkLogs to Icons.Default.Timeline,
        Screen.Notification to Icons.Default.Notifications,
        Screen.Account to Icons.Default.AccountCircle
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xFF2C2C2C)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val currentScreen = backStack.lastOrNull() ?: Screen.Projects

        items.forEach { (screen, icon) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { onItemSelected(screen) }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = screen::class.simpleName,
                    tint = if (currentScreen == screen) Color.White else Color.Gray,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .width(30.dp)
                        .background(if (currentScreen == screen) Color.Red else Color.Transparent)
                )
            }
        }
    }
}
