package worklogtracker.webapp.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.auth.LoginRequest
import worklogtracker.webapp.ApiClient
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onLoginSuccess: (AuthResponse) -> Unit) {
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
            backgroundColor(Styles.Background)
        }
    }) {
        Div({
            style {
                padding(40.px)
                backgroundColor(Styles.Surface)
                borderRadius(12.px)
                property("box-shadow", "0 10px 15px -3px rgba(0,0,0,0.1), 0 4px 6px -2px rgba(0,0,0,0.05)")
                width(320.px)
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
            }
        }) {
            H2({
                style {
                    textAlign("center")
                    color(Styles.TextPrimary)
                    marginBottom(24.px)
                    marginTop(0.px)
                }
            }) { Text("Admin Portaal") }

            if (error.isNotEmpty()) {
                Div({
                    style {
                        color(Styles.Error)
                        marginBottom(16.px)
                        fontSize(0.9.em)
                        backgroundColor(Color("#FEE2E2"))
                        padding(10.px)
                        borderRadius(6.px)
                    }
                }) { Text(error) }
            }

            Div({ style { marginBottom(16.px) } }) {
                Label(forId = "email", attrs = {
                    style {
                        display(DisplayStyle.Block)
                        marginBottom(6.px)
                        color(Styles.TextSecondary)
                        fontSize(0.9.em)
                    }
                }) { Text("E-mailadres") }
                Input(org.jetbrains.compose.web.attributes.InputType.Email) {
                    id("email")
                    style {
                        width(100.percent)
                        padding(10.px)
                        boxSizing("border-box")
                        borderRadius(8.px)
                        border(1.px, LineStyle.Solid, Styles.Border)
                        outline("none")
                    }
                    value(email)
                    onInput { email = it.value }
                }
            }

            Div({ style { marginBottom(24.px) } }) {
                Label(forId = "password", attrs = {
                    style {
                        display(DisplayStyle.Block)
                        marginBottom(6.px)
                        color(Styles.TextSecondary)
                        fontSize(0.9.em)
                    }
                }) { Text("Wachtwoord") }
                Input(org.jetbrains.compose.web.attributes.InputType.Password) {
                    id("password")
                    style {
                        width(100.percent)
                        padding(10.px)
                        boxSizing("border-box")
                        borderRadius(8.px)
                        border(1.px, LineStyle.Solid, Styles.Border)
                        outline("none")
                    }
                    value(password)
                    onInput { password = it.value }
                }
            }

            Button({
                style {
                    width(100.percent)
                    padding(12.px)
                    backgroundColor(if (loading) Styles.Secondary else Styles.Primary)
                    color(Color.white)
                    border(0.px)
                    borderRadius(8.px)
                    cursor(if (loading) "default" else "pointer")
                    fontWeight("600")
                    property("transition", "background-color 0.2s")
                }
                disabled(loading)
                onClick {
                    if (email.isEmpty() || password.isEmpty()) {
                        error = "Vul alle velden in"
                        return@onClick
                    }
                    loading = true
                    error = ""
                    scope.launch {
                        try {
                            val response = api.login(LoginRequest(email, password))
                            if (response.role == "ADMIN" || response.role == "TEAM_LEADER") {
                                onLoginSuccess(response)
                            } else {
                                error = "Toegang geweigerd: Onvoldoende rechten"
                                loading = false
                            }
                        } catch (e: Exception) {
                            error = "Inloggen mislukt: Controleer uw gegevens"
                            loading = false
                        }
                    }
                }
            }) {
                Text(if (loading) "Bezig met inloggen..." else "Inloggen")
            }
        }
    }
}
