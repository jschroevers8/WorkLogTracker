package worklogtracker.presentation.user.address

import worklogtracker.data.remote.user.CreateAddressRequest
import worklogtracker.data.remote.user.CreateUser
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.UserRepository

class AddressViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<AddressUiState>(AddressUiState()) {

    var onAddressSaved: (() -> Unit)? = null

    fun saveAddress() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {

                val houseNumberInt = uiState.houseNumber.toIntOrNull()
                if (houseNumberInt == null) {
                    setError("House number must be numeric")
                    return@launchWithErrorHandling
                }

                val address = CreateAddressRequest(
                    city = uiState.city,
                    street = uiState.street,
                    houseNumber = houseNumberInt,
                    subHouseNumber = uiState.subHouseNumber,
                    postalCode = uiState.postalCode
                )

                userRepository.createAddress(address = address)
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
