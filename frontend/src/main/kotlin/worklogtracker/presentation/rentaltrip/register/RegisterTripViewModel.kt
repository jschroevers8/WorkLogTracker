package worklogtracker.presentation.rentaltrip.register

import worklogtracker.data.remote.rental.RegisterRentalTrip
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.RentalRepository

class RegisterTripViewModel(
    private val rentalRepository: RentalRepository
) : BaseViewModel<RegisterTripUiState>(RegisterTripUiState()) {

    var onSuccess: (() -> Unit)? = null

    fun registerTrip(rentalId: Int) {
        launchWithErrorHandling {
            val trip = RegisterRentalTrip(
                rentalId = rentalId,
                startMileage = uiState.startMileage.toInt(),
                endMileage = uiState.endMileage.toInt(),
                startDate = "${uiState.startDate}T00:00:00",
                endDate = "${uiState.endDate}T00:00:00",
            )

            rentalRepository.registerRentalTrip(trip)
            onSuccess?.invoke()
            resetState()
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
