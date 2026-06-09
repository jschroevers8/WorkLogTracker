package worklogtracker.presentation.admin.renterrequests

import worklogtracker.data.remote.user.UserResponse
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

class AdminRenterRequestsViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<AdminRenterRequestsUiState>(
    AdminRenterRequestsUiState()
) {

    init {
        loadRequests()
    }

    private fun loadRequests() {
        launchWithErrorHandling {
            val users: List<UserResponse> = userRepository.getRenterRequests()
            val requestsUiState = users.map { user ->
                RenterRequestUiState(
                    id = user.id,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                )
            }
            _uiState = uiState.copy(requests = requestsUiState)
        }
    }

    fun accept(id: Int) {
        launchWithErrorHandling {
            userRepository.acceptRenterRequest(id)
            loadRequests()
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
