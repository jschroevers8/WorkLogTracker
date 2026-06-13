package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.auth.LoginRequest
import worklogtracker.webapp.ApiClient

class LoginViewModel(
    private val api: ApiClient,
    private val scope: CoroutineScope,
    private val onLoginSuccess: (AuthResponse) -> Unit
) {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var error by mutableStateOf("")
    var loading by mutableStateOf(false)

    fun login() {
        if (email.isEmpty() || password.isEmpty()) {
            error = "Vul alle velden in"
            return
        }
        loading = true
        error = ""
        scope.launch {
            try {
                val response = api.auth.login(LoginRequest(email, password))

                if (response.role == "ADMIN") {
                    onLoginSuccess(response)
                } else {
                    error = "Toegang geweigerd: Alleen Admins kunnen inloggen op het admin portaal."
                }
            } catch (e: Throwable) {
                error = if (e.message?.contains("401") == true) {
                    "Ongeldig e-mailadres of wachtwoord."
                } else if (e.message?.contains("403") == true) {
                    "Je hebt geen toegang tot dit portaal."
                } else {
                    e.message ?: "Inloggen mislukt. Controleer je verbinding en probeer het opnieuw."
                }
            } finally {
                loading = false
            }
        }
    }
}
