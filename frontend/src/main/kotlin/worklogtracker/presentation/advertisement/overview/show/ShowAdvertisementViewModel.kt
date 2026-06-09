package worklogtracker.presentation.advertisement.overview.show

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import worklogtracker.data.local.AuthManagerInterface
import worklogtracker.data.remote.advertisement.AdvertisementResponse
import worklogtracker.presentation.advertisement.overview.general.uistates.AdvertisementUiState
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.AdvertisementRepository

class ShowAdvertisementViewModel(
    private val advertisementRepository: AdvertisementRepository,
    authManager: AuthManagerInterface
) : BaseViewModel<ShowAdvertisementUiState>(ShowAdvertisementUiState()) {

    init {
        authManager.authTokenFlow.onEach { token ->
            _uiState = uiState.copy(isLoggedIn = token != null)
        }.launchIn(viewModelScope)
    }

    fun loadAdvertisement(advertisementId: Int) {
        launchWithErrorHandling {
            val fetchedAd: AdvertisementResponse = advertisementRepository.getAdvertisementById(advertisementId)
            val adUiState = AdvertisementUiState(
                id = fetchedAd.id,
                availableFrom = fetchedAd.availableFrom,
                availableUntil = fetchedAd.availableUntil,
                price = fetchedAd.price.toString(),
                address = fetchedAd.address,
                car = fetchedAd.car
            )
            _uiState = uiState.copy(advertisement = adUiState)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
