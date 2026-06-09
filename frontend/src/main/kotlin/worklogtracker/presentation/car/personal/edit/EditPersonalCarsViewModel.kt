package worklogtracker.presentation.car.personal.edit

import androidx.compose.runtime.mutableStateListOf
import worklogtracker.data.remote.car.CarResponse
import worklogtracker.data.remote.car.CreateCarImage
import worklogtracker.data.remote.car.UpdateCar
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.CarRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditPersonalCarsViewModel(
    private val carRepository: CarRepository
) : BaseViewModel<EditPersonalCarsUiState>(EditPersonalCarsUiState()) {

    var onSuccess: (() -> Unit)? = null

    var carImages = mutableStateListOf<CreateCarImage>()
        private set

    fun addCarImage(image: String) {
        _uiState = uiState.copy(
            carImages = uiState.carImages + image
        )

        carImages.add(CreateCarImage(image = image))
    }

    fun loadCar(carId: Int) {
        launchWithErrorHandling {
            val fetchedCar: CarResponse = carRepository.getCarById(carId)

            _uiState = uiState.copy(
                brand = fetchedCar.brand,
                model = fetchedCar.model,
                modelYear = fetchedCar.modelYear,
                licensePlate = fetchedCar.licensePlate,
                mileage = fetchedCar.mileage,
                fuelType = fetchedCar.fuelType,
                bodyType = fetchedCar.bodyType,
                carImages = fetchedCar.carImages.map { it.image }
            )
        }
    }

    fun updateCar(carId: Int) {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                try {
                    val car = UpdateCar(
                        id = carId,
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


                    carRepository.updateCar(car)
                    onSuccess?.invoke()
                    resetState()
                } catch (e: Exception) {
                    setError(e.message ?: "Updating car failed")
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
