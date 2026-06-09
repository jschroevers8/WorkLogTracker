package worklogtracker.presentation.user.login

import worklogtracker.data.local.AuthManagerInterface
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
                val token = userRepository.login(
                    LoginRequest(uiState.email, uiState.password)
                )

                if (token.isNotEmpty()) {
                    authManager.saveAuthToken(token)
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
