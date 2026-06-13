package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.auth.LoginRequest
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.ui.Styles
import org.jetbrains.compose.web.attributes.disabled
import worklogtracker.webapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onLoginSuccess: (AuthResponse) -> Unit) {
    val viewModel = remember { LoginViewModel(api, scope, onLoginSuccess) }

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
                alignItems(AlignItems.Center)
            }
        }) {
            Img(src = "https://img.icons8.com/fluency/96/work.png") {
                style {
                    width(64.px)
                    height(64.px)
                    marginBottom(16.px)
                }
            }
            H2({
                style {
                    textAlign("center")
                    color(Styles.TextPrimary)
                    marginBottom(24.px)
                    marginTop(0.px)
                }
            }) { Text("Admin Portaal") }

            if (viewModel.error.isNotEmpty()) {
                Div({
                    style {
                        color(Styles.Error)
                        marginBottom(16.px)
                        fontSize(0.9.em)
                        backgroundColor(Color("#FEE2E2"))
                        padding(10.px)
                        borderRadius(6.px)
                        width(100.percent)
                        boxSizing("border-box")
                    }
                }) { Text(viewModel.error) }
            }

            Div({ style { marginBottom(16.px); width(100.percent) } }) {
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
                    value(viewModel.email)
                    onInput { viewModel.email = it.value }
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
                    value(viewModel.password)
                    onInput { viewModel.password = it.value }
                }
            }

            Button({
                if (viewModel.loading) {
                    disabled()
                }

                style {
                    width(100.percent)
                    padding(12.px)
                    backgroundColor(if (viewModel.loading) Styles.Secondary else Styles.Primary)
                    color(Color.white)
                    border(0.px)
                    borderRadius(8.px)
                    cursor(if (viewModel.loading) "default" else "pointer")
                    fontWeight("600")
                    property("transition", "background-color 0.2s")
                }
                onClick {
                    viewModel.login()
                }
            }) {
                Text(if (viewModel.loading) "Bezig met inloggen..." else "Inloggen")
            }
        }
    }
}
