package worklogtracker.presentation.car.create

import androidx.compose.runtime.mutableStateListOf
import worklogtracker.data.remote.car.CreateCar
import worklogtracker.data.remote.car.CreateCarImage
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.CarRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateCarViewModel(
    private val carRepository: CarRepository
) : BaseViewModel<CreateCarUiState>(CreateCarUiState()) {

    var onSuccess: (() -> Unit)? = null

    var carImages = mutableStateListOf<CreateCarImage>()
        private set

    fun addCarImage(image: String) {
        carImages.add(CreateCarImage(image = image))
    }

    fun createCar() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                try {
                    val car = CreateCar(
                        brand = uiState.brand,
                        model = uiState.model,
                        modelYear = uiState.modelYear,
                        licensePlate = uiState.licensePlate,
                        mileage = uiState.mileage,
                        fuelType = uiState.fuelType,
                        bodyType = uiState.bodyType,
                        createdStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                        carImages = carImages.toList(),
                    )

                    carRepository.createCar(car)
                    onSuccess?.invoke()
                    resetState()
                } catch (e: Exception) {
                    setError(e.message ?: "Create car failed")
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
