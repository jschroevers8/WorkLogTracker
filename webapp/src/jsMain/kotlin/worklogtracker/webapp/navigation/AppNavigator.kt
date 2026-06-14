package worklogtracker.webapp.navigation

import androidx.compose.runtime.*
import kotlinx.browser.localStorage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.koin.compose.koinInject
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.NavLink
import worklogtracker.webapp.ui.screens.*

@Composable
fun AppNavigator() {
    val apiClient = koinInject<ApiClient>()
    var currentUser by remember {
        mutableStateOf(
            localStorage.getItem("auth_response")?.let {
                try {
                    Json.decodeFromString<AuthResponse>(it)
                } catch (e: Exception) {
                    null
                }
            },
        )
    }

    LaunchedEffect(currentUser) {
        apiClient.setToken(currentUser?.token)
    }

    val backStack = remember { mutableStateListOf<Screen>(if (currentUser != null) Screen.Dashboard else Screen.Login) }
    val currentScreen = backStack.lastOrNull() ?: Screen.Login
    val scope = rememberCoroutineScope()

    val onNavigate: (Screen) -> Unit = { screen ->
        backStack.add(screen)
    }

    val onBack: () -> Unit = {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }

    if (currentScreen.requiresAuth && currentUser == null) {
        LaunchedEffect(currentScreen) {
            backStack.clear()
            backStack.add(Screen.Login)
        }
    } else {
        if (currentScreen is Screen.Login) {
            LoginScreen(scope) { user ->
                currentUser = user
                localStorage.setItem("auth_response", Json.encodeToString(user))
                apiClient.setToken(user.token)
                backStack.clear()
                backStack.add(Screen.Dashboard)
            }
        } else {
            MainLayout(
                currentUser,
                onLogout = {
                    currentUser = null
                    localStorage.removeItem("auth_response")
                    apiClient.setToken(null)
                    backStack.clear()
                    backStack.add(Screen.Login)
                },
                currentScreen = currentScreen,
                onNavigate = onNavigate
            ) {
                when (currentScreen) {
                    is Screen.Dashboard -> DashboardScreen()
                    is Screen.Employees -> EmployeesScreen { userId ->
                        onNavigate(Screen.EmployeeDetail(userId))
                    }
                    is Screen.Projects -> ProjectsScreen { projectId ->
                        onNavigate(Screen.ProjectDetail(projectId))
                    }
                    is Screen.EmployeeDetail -> EmployeeDetailScreen(currentScreen.userId) {
                        onBack()
                    }
                    is Screen.ProjectDetail -> ProjectDetailScreen(currentScreen.projectId) {
                        onBack()
                    }
                    else -> {}
                }
            }
        }
    }
}
