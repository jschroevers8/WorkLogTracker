package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.ui.Styles
import kotlinx.coroutines.launch

@Composable
fun EmployeesScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onUserSelected: (Int) -> Unit) {
    var users by remember { mutableStateOf<List<UserResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            users = api.users.getUsers()
        } catch (e: Exception) {
            error = "Fout bij ophalen medewerkers: ${e.message}"
        } finally {
            loading = false
        }
    }

    H2({
        style {
            color(Styles.TextPrimary)
            marginBottom(24.px)
        }
    }) { Text("Medewerkers") }

    if (loading) {
        P { Text("Laden...") }
    } else if (error.isNotEmpty()) {
        P({ style { color(Styles.Error) } }) { Text(error) }
    } else {
        Div({
            style {
                backgroundColor(Styles.Surface)
                borderRadius(12.px)
                property("box-shadow", "0 1px 3px rgba(0,0,0,0.1)")
                overflow("hidden")
            }
        }) {
            Table({
                style {
                    width(100.percent)
                    property("border-collapse", "collapse")
                }
            }) {
                Thead {
                    Tr({
                        style {
                            backgroundColor(Color("#F8FAFC"))
                            property("border-bottom", "1px solid ${Styles.Border}")
                        }
                    }) {
                        Th({ style { textAlign("left"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Naam") }
                        Th({ style { textAlign("left"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Email") }
                        Th({ style { textAlign("left"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Rol") }
                        Th({ style { textAlign("right"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Acties") }
                    }
                }
                Tbody {
                    users.forEach { user ->
                        Tr({
                            style {
                                property("border-bottom", "1px solid ${Styles.Border}")
                                property("transition", "background-color 0.2s")
                            }
                        }) {
                            Td({ style { padding(16.px); color(Styles.TextPrimary) } }) { Text("${user.firstName} ${user.lastName}") }
                            Td({ style { padding(16.px); color(Styles.TextSecondary) } }) { Text(user.email) }
                            Td({ style { padding(16.px) } }) {
                                Span({
                                    style {
                                        padding(4.px, 8.px)
                                        borderRadius(12.px)
                                        fontSize(0.8.em)
                                        fontWeight("600")
                                        if (user.role == "ADMIN") {
                                            backgroundColor(Color("#DBEAFE"))
                                            color(Color("#1E40AF"))
                                        } else {
                                            backgroundColor(Color("#F1F5F9"))
                                            color(Styles.TextSecondary)
                                        }
                                    }
                                }) { Text(user.role) }
                            }
                            Td({ style { padding(16.px); textAlign("right") } }) {
                                Button({
                                    style {
                                        padding(8.px, 12.px)
                                        backgroundColor(Styles.Primary)
                                        color(Color.white)
                                        border(0.px)
                                        borderRadius(6.px)
                                        cursor("pointer")
                                        fontSize(0.9.em)
                                        fontWeight("500")
                                    }
                                    onClick { onUserSelected(user.id.toInt()) }
                                }) { Text("Bekijk uren") }
                            }
                        }
                    }
                }
            }
        }
    }
}
