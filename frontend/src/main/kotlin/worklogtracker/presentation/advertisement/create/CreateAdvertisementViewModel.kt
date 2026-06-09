package worklogtracker.presentation.advertisement.create

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import worklogtracker.data.remote.address.AddressResponse
import worklogtracker.data.remote.advertisement.CreateAdvertisement
import worklogtracker.data.remote.car.CarResponse
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.AdvertisementRepository
import worklogtracker.repositories.CarRepository
import worklogtracker.repositories.UserRepository


class CreateAdvertisementViewModel(
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
    private val advertisementRepository: AdvertisementRepository

) : BaseViewModel<CreateAdvertisementUiState>(CreateAdvertisementUiState()) {
    var onSuccess: (() -> Unit)? = null

    fun loadInitialData() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val carsDeferred = async { carRepository.getPersonalCars() }
                val addressesDeferred = async { userRepository.getUser().addresses }

                _uiState = uiState.copy(
                    cars = carsDeferred.await(),
                    addresses = addressesDeferred.await(),
                    loading = false
                )
            } catch (e: Exception) {
                setError(e.message)
                setLoading(false)
            }
        }
    }

    fun createAdvertisement() {
        if (uiState.validate(::setError)) {
            launchWithErrorHandling {
                try {
                    val advertisement = CreateAdvertisement(
                        carId = uiState.carId.toInt(),
                        addressId = uiState.addressId.toInt(),
                        availableFrom = "${uiState.availableFrom}T00:00:00",
                        availableUntil = "${uiState.availableUntil}T00:00:00",
                        price = uiState.price.toDouble()
                    )

                    advertisementRepository.createAdvertisement(advertisement)
                    onSuccess?.invoke()
                    resetState()
                } catch (e: Exception) {
                    setError(e.message ?: "Advertisement creation failed")
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