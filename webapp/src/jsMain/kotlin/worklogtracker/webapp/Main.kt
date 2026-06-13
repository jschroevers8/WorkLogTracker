package worklogtracker.webapp

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.NavLink
import worklogtracker.webapp.ui.screens.*

enum class Screen {
    LOGIN, DASHBOARD, EMPLOYEES, PROJECTS, EMPLOYEE_DETAIL, PROJECT_DETAIL, WORK_LOGS
}

val apiClient = ApiClient()

fun main() {
    renderComposable(rootElementId = "root") {
        var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
        var currentUser by remember { mutableStateOf<AuthResponse?>(null) }
        var selectedUserId by remember { mutableStateOf<Int?>(null) }
        var selectedProjectId by remember { mutableStateOf<Int?>(null) }
        val scope = rememberCoroutineScope()

        if (currentScreen == Screen.LOGIN) {
            LoginScreen(apiClient, scope) { user ->
                currentUser = user
                apiClient.setToken(user.token)
                currentScreen = Screen.DASHBOARD
            }
        } else {
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
                        }
                        onClick { currentScreen = Screen.DASHBOARD }
                    }) { Text("WLT Admin") }

                    NavLink("Dashboard", currentScreen == Screen.DASHBOARD) { currentScreen = Screen.DASHBOARD }
                    NavLink("Medewerkers", currentScreen == Screen.EMPLOYEES || currentScreen == Screen.EMPLOYEE_DETAIL) { currentScreen = Screen.EMPLOYEES }
                    NavLink("Projecten", currentScreen == Screen.PROJECTS || currentScreen == Screen.PROJECT_DETAIL) { currentScreen = Screen.PROJECTS }

                    Div({
                        style {
//                            marginLeft(java.lang.String("auto"))
                            display(DisplayStyle.Flex)
                            alignItems(AlignItems.Center)
                        }
                    }) {
                        Div({
                            style {
                                marginRight(16.px)
                                textAlign("right")
                            }
                        }) {
                            Div({ style { fontWeight("600"); fontSize(0.9.em); color(Styles.TextPrimary) } }) {
                                Text("${currentUser?.firstName} ${currentUser?.lastName}")
                            }
                            Div({ style { fontSize(0.75.em); color(Styles.TextSecondary) } }) {
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
                            onClick {
                                currentUser = null
                                apiClient.setToken(null)
                                currentScreen = Screen.LOGIN
                            }
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
                    // Page Content
                    Div({
                        style {
                            padding(32.px)
                            flex(1)
                            overflowY("auto")
                        }
                    }) {
                        when (currentScreen) {
                            Screen.DASHBOARD -> DashboardScreen()
                            Screen.EMPLOYEES -> EmployeesScreen(apiClient, scope) { id ->
                                selectedUserId = id
                                currentScreen = Screen.EMPLOYEE_DETAIL
                            }
                            Screen.PROJECTS -> ProjectsScreen(apiClient, scope) { id ->
                                selectedProjectId = id
                                currentScreen = Screen.PROJECT_DETAIL
                            }
                            Screen.EMPLOYEE_DETAIL -> EmployeeDetailScreen(selectedUserId!!, apiClient, scope) {
                                currentScreen = Screen.EMPLOYEES
                            }
                            Screen.PROJECT_DETAIL -> ProjectDetailScreen(selectedProjectId!!, apiClient, scope) {
                                currentScreen = Screen.PROJECTS
                            }
                            Screen.WORK_LOGS -> WorkLogRegistrationScreen(apiClient, scope)
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
