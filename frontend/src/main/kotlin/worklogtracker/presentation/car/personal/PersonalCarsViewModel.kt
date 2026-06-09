package worklogtracker.presentation.car.personal

import worklogtracker.data.remote.car.CarResponse
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.CarRepository

class PersonalCarsViewModel(
    private val carRepository: CarRepository,
) : BaseViewModel<PersonalCarsUiState>(PersonalCarsUiState()) {

    fun loadPersonalCars() {
        launchWithErrorHandling {
            val fetchedCars: List<CarResponse> = carRepository.getPersonalCars()
            _uiState = uiState.copy(cars = fetchedCars)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
