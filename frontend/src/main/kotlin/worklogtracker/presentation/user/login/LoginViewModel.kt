package worklogtracker.presentation.user.login

import worklogtracker.data.auth.AuthManagerInterface
import worklogtracker.data.auth.UserData
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

import worklogtracker.shared.dto.auth.LoginRequest

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authManager: AuthManagerInterface
) : BaseViewModel<LoginUiState>(LoginUiState()) {

    var onLoginSuccess: (() -> Unit)? = null

    fun login() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                val authResponse = userRepository.login(
                    LoginRequest(uiState.email, uiState.password)
                )

                if (authResponse.token.isNotEmpty()) {
                    authManager.saveAuthToken(authResponse.token)
                    authManager.saveUserData(
                        UserData(
                            firstName = authResponse.firstName,
                            lastName = authResponse.lastName,
                            email = authResponse.email,
                            role = authResponse.role
                        )
                    )
                    onLoginSuccess?.invoke()
                    resetState()
                } else {
                    setError("Login failed")
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
