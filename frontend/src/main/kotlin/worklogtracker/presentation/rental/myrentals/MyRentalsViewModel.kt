package worklogtracker.presentation.rental.myrentals

import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.RentalRepository

class MyRentalsViewModel(
    private val rentalRepository: RentalRepository
) : BaseViewModel<MyRentalsUiState>(MyRentalsUiState()) {

    fun loadRentals() {
        launchWithErrorHandling {
            val rentals = rentalRepository.getUserRentals()
            _uiState = uiState.copy(rentals = rentals)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}

