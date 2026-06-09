package worklogtracker.webapp

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import worklogtracker.shared.dto.auth.LoginRequest

fun main() {
    renderComposable(rootElementId = "root") {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Div({
            style {
                padding(20.px)
                fontFamily("Arial, sans-serif")
            }
        }) {

            H1 { Text("WorkLogTracker Admin") }

            // EMAIL
            Div {
                Label(forId = "email") { Text("Email: ") }
                Input(InputType.Email) {
                    id("email")
                    value(email)
                    onInput { email = it.value }
                }
            }

            Br()

            // PASSWORD
            Div {
                Label(forId = "password") { Text("Wachtwoord: ") }
                Input(InputType.Password) {
                    id("password")
                    value(password)
                    onInput { password = it.value }
                }
            }

            Br()

            // LOGIN BUTTON
            Button({
                onClick {
                    val loginRequest = LoginRequest(email, password)
                    println("Inloggen met: $loginRequest")

                    // TODO: Ktor API call
                }
            }) {
                Text("Inloggen")
            }
        }
    }
}