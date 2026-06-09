package worklogtracker.presentation.user.account

import worklogtracker.data.local.AuthManagerInterface
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

class AccountViewModel(
    private val userRepository: UserRepository,
    private val authManager: AuthManagerInterface,
) : BaseViewModel<AccountUiState>(AccountUiState()) {

    var onLogoutSuccess: (() -> Unit)? = null

    fun loadUser() {
        launchWithErrorHandling {
            val fetchedUser = userRepository.getUser()

            _uiState = uiState.copy(
                firstName = fetchedUser.firstName,
                lastName = fetchedUser.lastName,
                email = fetchedUser.email,
                phone = fetchedUser.phone,
                userType = fetchedUser.userType,
                renterRightsRequested = fetchedUser.renterRightsRequested,
                userPoints = fetchedUser.userPoints
            )
        }
    }

    fun logout() {
        launchWithErrorHandling {
            authManager.clearAuthToken()
            onLogoutSuccess?.invoke()
        }
    }

    fun requestRenterRoll() {
        launchWithErrorHandling {
            userRepository.requestRenter()

            loadUser()
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
