package worklogtracker.presentation.rental.create

import worklogtracker.data.remote.rental.CreateRental
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.RentalRepository

class CreateRentalViewModel(
    private val reservationRepository: RentalRepository
) : BaseViewModel<CreateRentalUiState>(CreateRentalUiState()) {

    var onSuccess: (() -> Unit)? = null

    fun createReservation(advertisementId: Int) {
        launchWithErrorHandling {
            try {
                val reservation = CreateRental(
                    advertisementId = advertisementId,
                    pickUpDate = "${uiState.pickUpDate}T00:00:00",
                    returningDate = "${uiState.returningDate}T00:00:00",
                )

                reservationRepository.createRental(reservation)
                onSuccess?.invoke()
                resetState()
            } catch (e: Exception) {
                setError(e.message ?: "Reservation creation failed")
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
