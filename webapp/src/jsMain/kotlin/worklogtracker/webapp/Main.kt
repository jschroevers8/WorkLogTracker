package worklogtracker.webapp

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.shared.dto.auth.LoginRequest
import kotlinx.coroutines.launch

enum class Screen {
    LOGIN, DASHBOARD, EMPLOYEES, PROJECTS, EMPLOYEE_DETAIL
}

val apiClient = ApiClient()

fun main() {
    renderComposable(rootElementId = "root") {
        var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
        var currentUser by remember { mutableStateOf<AuthResponse?>(null) }
        var selectedUserId by remember { mutableStateOf<Int?>(null) }
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
                    fontFamily("Arial, sans-serif")
                }
            }) {
                // Header / Nav
                Nav({
                    style {
                        display(DisplayStyle.Flex)
                        backgroundColor(Color("#333"))
                        color(Color.white)
                        padding(10.px)
                        alignItems(AlignItems.Center)
                    }
                }) {
                    Div({
                        style {
                            marginRight(20.px)
                            fontWeight("bold")
                            cursor("pointer")
                            fontSize(1.2.em)
                        }
                        onClick { currentScreen = Screen.DASHBOARD }
                    }) { Text("WorkLogTracker Admin") }

                    NavLink("Dashboard", currentScreen == Screen.DASHBOARD) { currentScreen = Screen.DASHBOARD }
                    NavLink("Medewerkers", currentScreen == Screen.EMPLOYEES || currentScreen == Screen.EMPLOYEE_DETAIL) { currentScreen = Screen.EMPLOYEES }
                    NavLink("Projecten", currentScreen == Screen.PROJECTS) { currentScreen = Screen.PROJECTS }

                    Div({
                        style { marginLeft(java.lang.String("auto")); cursor("pointer"); display(DisplayStyle.Flex); alignItems(AlignItems.Center) }
                    }) {
                        Span({ style { marginRight(10.px); fontSize(0.9.em) } }) { Text("${currentUser?.firstName} (${currentUser?.role})") }
                        Button({
                            style {
                                backgroundColor(Color.transparent)
                                color(Color.white)
                                border(1.px, LineStyle.Solid, Color.white)
                                borderRadius(4.px)
                                padding(5.px, 10.px)
                                cursor("pointer")
                            }
                            onClick {
                                currentUser = null
                                apiClient.setToken(null)
                                currentScreen = Screen.LOGIN
                            }
                        }) { Text("Uitloggen") }
                    }
                }

                // Content
                Div({
                    style {
                        padding(20.px)
                        flex(1)
                        backgroundColor(Color("#f8f9fa"))
                        overflowY("auto")
                    }
                }) {
                    when (currentScreen) {
                        Screen.DASHBOARD -> DashboardScreen()
                        Screen.EMPLOYEES -> EmployeesScreen(apiClient, scope) { id ->
                            selectedUserId = id
                            currentScreen = Screen.EMPLOYEE_DETAIL
                        }
                        Screen.PROJECTS -> ProjectsScreen(apiClient, scope)
                        Screen.EMPLOYEE_DETAIL -> EmployeeDetailScreen(selectedUserId!!, apiClient, scope) {
                            currentScreen = Screen.EMPLOYEES
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun NavLink(text: String, active: Boolean, onClick: () -> Unit) {
    Div({
        style {
            marginRight(15.px)
            cursor("pointer")
            if (active) {
                borderBottom(2.px, LineStyle.Solid, Color.white)
            }
            padding(5.px, 0.px)
        }
        onClick { onClick() }
    }) { Text(text) }
}

@Composable
fun LoginScreen(api: ApiClient, scope: androidx.compose.runtime.Applier<*>? = null, coroutineScope: kotlinx.coroutines.CoroutineScope, onLoginSuccess: (AuthResponse) -> Unit) {
    // Redefining because of scope parameter mismatch in previous attempt, but we'll use the coroutineScope
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Div({
        style {
            display(DisplayStyle.Flex)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.Center)
            height(100.vh)
            backgroundColor(Color("#f0f2f5"))
        }
    }) {
        Div({
            style {
                padding(40.px)
                backgroundColor(Color.white)
                borderRadius(8.px)
                boxShadow("0 2px 4px rgba(0,0,0,0.1)")
                width(300.px)
            }
        }) {
            H2 { Text("Inloggen Admin") }

            if (error.isNotEmpty()) {
                Div({ style { color(Color.red); marginBottom(10.px); fontSize(0.9.em) } }) { Text(error) }
            }

            Div({ style { marginBottom(15.px) } }) {
                Label(forId = "email") { Text("Email") }
                Br()
                Input(org.jetbrains.compose.web.attributes.InputType.Email) {
                    id("email")
                    style { width(100.percent); padding(8.px); boxSizing("border-box"); borderRadius(4.px); border(1.px, LineStyle.Solid, Color("#ccc")) }
                    value(email)
                    onInput { email = it.value }
                }
            }

            Div({ style { marginBottom(20.px) } }) {
                Label(forId = "password") { Text("Wachtwoord") }
                Br()
                Input(org.jetbrains.compose.web.attributes.InputType.Password) {
                    id("password")
                    style { width(100.percent); padding(8.px); boxSizing("border-box"); borderRadius(4.px); border(1.px, LineStyle.Solid, Color("#ccc")) }
                    value(password)
                    onInput { password = it.value }
                }
            }

            Button({
                style {
                    width(100.percent)
                    padding(10.px)
                    backgroundColor(if (loading) Color("#ccc") else Color("#007bff"))
                    color(Color.white)
                    border(0.px)
                    borderRadius(4.px)
                    cursor(if (loading) "default" else "pointer")
                }
                disabled(loading)
                onClick {
                    if (email.isEmpty() || password.isEmpty()) {
                        error = "Vul alle velden in"
                        return@onClick
                    }
                    loading = true
                    error = ""
                    coroutineScope.launch {
                        try {
                            val response = api.login(LoginRequest(email, password))
                            if (response.role == "ADMIN" || response.role == "TEAM_LEADER") {
                                onLoginSuccess(response)
                            } else {
                                error = "Toegang geweigerd: Je bent geen Admin of Teamleider"
                            }
                        } catch (e: Exception) {
                            error = "Inloggen mislukt: ${e.message}"
                        } finally {
                            loading = false
                        }
                    }
                }
            }) {
                Text(if (loading) "Bezig..." else "Inloggen")
            }
        }
    }
}

// Wrapper for the LoginScreen to handle the scope correctly in main
@Composable
fun LoginScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onLoginSuccess: (AuthResponse) -> Unit) {
    LoginScreen(api, null, scope, onLoginSuccess)
}

@Composable
fun EmployeesScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onUserSelected: (Int) -> Unit) {
    var users by remember { mutableStateOf<List<UserResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            users = api.getUsers()
        } catch (e: Exception) {
            error = "Fout bij ophalen medewerkers: ${e.message}"
        } finally {
            loading = false
        }
    }

    H2 { Text("Medewerkers") }

    if (loading) {
        P { Text("Laden...") }
    } else if (error.isNotEmpty()) {
        P({ style { color(Color.red) } }) { Text(error) }
    } else {
        Table({
            style {
                width(100.percent)
                borderCollapse("collapse")
                backgroundColor(Color.white)
                borderRadius(8.px)
                overflow("hidden")
                boxShadow("0 1px 3px rgba(0,0,0,0.1)")
            }
        }) {
            Thead {
                Tr({ style { backgroundColor(Color("#eee")) } }) {
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Naam") }
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Email") }
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Rol") }
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Acties") }
                }
            }
            Tbody {
                users.forEach { user ->
                    Tr {
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) { Text("${user.firstName} ${user.lastName}") }
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) { Text(user.email) }
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) { Text(user.role) }
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) {
                            Button({
                                style {
                                    padding(5.px, 10.px)
                                    backgroundColor(Color("#28a745"))
                                    color(Color.white)
                                    border(0.px)
                                    borderRadius(4.px)
                                    cursor("pointer")
                                }
                                onClick { onUserSelected(user.id.toInt()) }
                            }) { Text("Uren bekijken") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmployeeDetailScreen(userId: Int, api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onBack: () -> Unit) {
    var worklogs by remember { mutableStateOf<List<WorkLogResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        try {
            worklogs = api.getUserWorkLogs(userId)
        } catch (e: Exception) {
            error = "Fout bij ophalen uren: ${e.message}"
        } finally {
            loading = false
        }
    }

    Div({ style { marginBottom(20.px) } }) {
        Button({
            style {
                padding(5.px, 10.px)
                cursor("pointer")
                marginRight(10.px)
            }
            onClick { onBack() }
        }) { Text("← Terug naar lijst") }
        H2 { Text("Urenoverzicht Medewerker #$userId") }
    }

    if (loading) {
        P { Text("Laden...") }
    } else if (error.isNotEmpty()) {
        P({ style { color(Color.red) } }) { Text(error) }
    } else if (worklogs.isEmpty()) {
        P { Text("Geen gewerkte uren gevonden voor deze medewerker.") }
    } else {
        Table({
            style {
                width(100.percent)
                borderCollapse("collapse")
                backgroundColor(Color.white)
                borderRadius(8.px)
                boxShadow("0 1px 3px rgba(0,0,0,0.1)")
            }
        }) {
            Thead {
                Tr({ style { backgroundColor(Color("#eee")) } }) {
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Datum/Tijd") }
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Duur (min)") }
                    Th({ style { textAlign("left"); padding(12.px) } }) { Text("Notities") }
                }
            }
            Tbody {
                worklogs.forEach { log ->
                    Tr {
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) { Text(log.startTime) }
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) { Text(log.durationMinutes?.toString() ?: "-") }
                        Td({ style { padding(12.px); borderBottom(1.px, LineStyle.Solid, Color("#eee")) } }) { Text(log.notes ?: "") }
                    }
                }
            }
        }
    }
}