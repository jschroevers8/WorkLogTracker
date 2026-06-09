package worklogtracker.presentation.user.address

import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

class AddressViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<AddressUiState>(AddressUiState()) {

    var onAddressSaved: (() -> Unit)? = null

    fun saveAddress() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                // Address functionality needs to be updated for new backend
                onAddressSaved?.invoke()
                resetState()
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
