package worklogtracker.presentation.user.signup

import worklogtracker.shared.dto.auth.RegisterUserRequest

class SignupViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<SignupUiState>(SignupUiState()) {

    var onSuccess: (() -> Unit)? = null

    fun signup() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                try {
                    val request = RegisterUserRequest(
                        email = uiState.email,
                        password = uiState.password,
                        firstName = uiState.firstName,
                        lastName = uiState.lastName
                    )

                    userRepository.register(request)
                    onSuccess?.invoke()
                    resetState()
                } catch (e: Exception) {
                    setError(e.message ?: "Signup failed")
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
