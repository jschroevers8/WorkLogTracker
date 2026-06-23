package worklogtracker.frontend.presentation.user.login

import worklogtracker.frontend.data.auth.AuthManagerInterface
import worklogtracker.frontend.data.auth.UserData
import worklogtracker.frontend.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.frontend.repositories.UserRepository
import worklogtracker.shared.dto.auth.LoginRequest

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authManager: AuthManagerInterface,
) : BaseViewModel<LoginUiState>(LoginUiState()) {
    var onLoginSuccess: (() -> Unit)? = null

    fun login() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                try {
                    val authResponse =
                        userRepository.login(
                            LoginRequest(uiState.email, uiState.password),
                        )

                    if (authResponse.token.isNotEmpty()) {
                        authManager.saveAuthToken(authResponse.token)
                        authManager.saveUserData(
                            UserData(
                                firstName = authResponse.firstName,
                                lastName = authResponse.lastName,
                                email = authResponse.email,
                                role = authResponse.role,
                            ),
                        )
                        onLoginSuccess?.invoke()
                        resetState()
                    } else {
                        setError("Login failed")
                    }
                } catch (e: Exception) {
                    setError("Ongeldig e-mailadres of wachtwoord.")
                }
            }
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
