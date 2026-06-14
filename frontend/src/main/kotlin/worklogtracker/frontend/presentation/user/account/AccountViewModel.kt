package worklogtracker.frontend.presentation.user.account

import kotlinx.coroutines.flow.firstOrNull
import worklogtracker.frontend.data.auth.AuthManagerInterface
import worklogtracker.frontend.presentation.framework.viewmodel.BaseViewModel

class AccountViewModel(
    private val authManager: AuthManagerInterface,
) : BaseViewModel<AccountUiState>(AccountUiState()) {
    var onLogoutSuccess: (() -> Unit)? = null

    fun loadUser() {
        launchWithErrorHandling {
            val userData = authManager.userDataFlow.firstOrNull()
            _uiState =
                uiState.copy(
                    firstName = userData?.firstName ?: "User",
                    lastName = userData?.lastName ?: "",
                    email = userData?.email ?: "",
                    phone = "",
                )
        }
    }

    fun logout() {
        launchWithErrorHandling {
            authManager.clearAuthToken()
            authManager.clearUserData()
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
