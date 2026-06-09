package worklogtracker.presentation.user.login

import worklogtracker.data.local.AuthManagerInterface
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authManager: AuthManagerInterface
) : BaseViewModel<LoginUiState>(LoginUiState()) {

    var onLoginSuccess: (() -> Unit)? = null

    fun login() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                val response = userRepository.loginUser(
                    uiState.email,
                    uiState.password
                )

                if (response.token != null) {
                    authManager.saveAuthToken(response.token)
                    onLoginSuccess?.invoke()
                    resetState()
                } else {
                    setError(response.error ?: "Login failed")
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
