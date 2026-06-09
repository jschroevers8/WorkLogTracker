package worklogtracker.presentation.user.account

import worklogtracker.data.auth.AuthManagerInterface
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

class AccountViewModel(
    private val userRepository: UserRepository,
    private val authManager: AuthManagerInterface,
) : BaseViewModel<AccountUiState>(AccountUiState()) {

    var onLogoutSuccess: (() -> Unit)? = null

    fun loadUser() {
        launchWithErrorHandling {
            _uiState = uiState.copy(
                firstName = "User",
                lastName = "",
                email = "",
                phone = ""
            )
        }
    }

    fun logout() {
        launchWithErrorHandling {
            authManager.clearAuthToken()
            onLogoutSuccess?.invoke()
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
