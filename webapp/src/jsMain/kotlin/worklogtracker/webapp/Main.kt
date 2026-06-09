package worklogtracker.webapp

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import worklogtracker.shared.dto.auth.LoginRequest

fun main() {
    renderComposable(rootElementId = "root") {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Div({ style { padding(20.px) } }) {
            H1 { Text("WorkLogTracker Admin") }
            
            Div {
                Label(forId = "email") { Text("Email: ") }
                Input(type = org.jetbrains.compose.web.attributes.InputType.Email) {
                    id("email")
                    value(email)
                    onInput { email = it.value }
                }
            }
            
            Div {
                Label(forId = "password") { Text("Wachtwoord: ") }
                Input(type = org.jetbrains.compose.web.attributes.InputType.Password) {
                    id("password")
                    value(password)
                    onInput { password = it.value }
                }
            }

            Button(attrs = {
                onClick {
                    val loginRequest = LoginRequest(email, password)
                    println("Inloggen met: $loginRequest")
                    // Hier komt de API aanroep
                }
            }) {
                Text("Inloggen")
            }
        }
    }
}
