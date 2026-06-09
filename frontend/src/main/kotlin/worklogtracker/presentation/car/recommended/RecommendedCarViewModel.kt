package worklogtracker.presentation.car.recommended

import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.CarRepository

class RecommendedCarViewModel(
    private val carRepository: CarRepository
) : BaseViewModel<RecommendedCarUiState>(RecommendedCarUiState()) {

    fun onInputChanged(text: String) {
        _uiState = _uiState.copy(inputText = text)
    }

    fun getRecommendedCar() {
        val question = _uiState.inputText
        if (question.isBlank()) return

        _uiState = _uiState.copy(
            lastQuestion = question,
            inputText = "",
            recommendedMessage = null,
            carId = null,
            error = null
        )

        launchWithErrorHandling {
            val response = carRepository.getRecommendedCar(question)
            _uiState = _uiState.copy(
                recommendedMessage = response.message,
                carId = response.carId
            )
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = _uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = _uiState.copy(error = message)
    }
}
