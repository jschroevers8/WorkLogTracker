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

@Composable
fun MainLayout(
    currentUser: AuthResponse?,
    onLogout: () -> Unit,
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    content: @Composable () -> Unit
) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            height(100.vh)
            fontFamily("'Inter', 'Segoe UI', Arial, sans-serif")
            backgroundColor(Styles.Background)
        }
    }) {
        Nav({
            style {
                display(DisplayStyle.Flex)
                backgroundColor(Styles.Surface)
                color(Styles.TextPrimary)
                padding(0.px, 24.px)
                height(64.px)
                alignItems(AlignItems.Center)
                property("box-shadow", "0 1px 2px 0 rgba(0, 0, 0, 0.05)")
                property("z-index", 100)
            }
        }) {
            Div({
                style {
                    marginRight(40.px)
                    fontWeight("800")
                    cursor("pointer")
                    fontSize(1.25.em)
                    color(Styles.Primary)
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                    gap(8.px)
                }
                onClick { onNavigate(Screen.Dashboard) }
            }) {
                Img(src = "https://img.icons8.com/fluency/48/work.png") {
                    style {
                        width(24.px)
                        height(24.px)
                    }
                }
                Text("WLT Admin")
            }

            NavLink("Dashboard", currentScreen is Screen.Dashboard) { onNavigate(Screen.Dashboard) }
            NavLink("Medewerkers", currentScreen is Screen.Employees || currentScreen is Screen.EmployeeDetail) {
                onNavigate(Screen.Employees)
            }
            NavLink("Projecten", currentScreen is Screen.Projects || currentScreen is Screen.ProjectDetail) {
                onNavigate(Screen.Projects)
            }

            Div({
                style {
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                    property("margin-left", "auto")
                }
            }) {
                Div({
                    style {
                        marginRight(16.px)
                        textAlign("right")
                    }
                }) {
                    Div({
                        style {
                            fontWeight("600")
                            fontSize(0.9.em)
                            color(Styles.TextPrimary)
                        }
                    }) {
                        Text("${currentUser?.firstName} ${currentUser?.lastName}")
                    }
                    Div({
                        style {
                            fontSize(0.75.em)
                            color(Styles.TextSecondary)
                        }
                    }) {
                        Text(currentUser?.role ?: "")
                    }
                }

                Button({
                    style {
                        backgroundColor(Color.white)
                        color(Styles.Error)
                        border(1.px, LineStyle.Solid, Color("#FEE2E2"))
                        borderRadius(6.px)
                        padding(6.px, 12.px)
                        cursor("pointer")
                        fontSize(0.85.em)
                        fontWeight("500")
                    }
                    onClick { onLogout() }
                }) { Text("Log uit") }
            }
        }

        // Main Content Area
        Div({
            style {
                display(DisplayStyle.Flex)
                flex(1)
                overflow("hidden")
            }
        }) {
            Div({
                style {
                    padding(32.px)
                    flex(1)
                    overflowY("auto")
                }
            }) {
                content()
            }
        }
    }
}
