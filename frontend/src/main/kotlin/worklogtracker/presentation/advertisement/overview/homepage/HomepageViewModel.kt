package worklogtracker.presentation.advertisement.overview.homepage

import worklogtracker.data.remote.advertisement.AdvertisementResponse
import worklogtracker.presentation.advertisement.overview.general.uistates.AdvertisementUiState
import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.AdvertisementRepository

class HomepageViewModel(
    private val advertisementRepository: AdvertisementRepository,
) : BaseViewModel<HomepageUiState>(HomepageUiState()) {

    private var allAdvertisements: List<AdvertisementUiState> = emptyList()

    fun onSearchQueryChanged(query: String) {
        val filteredList = if (query.isBlank()) {
            allAdvertisements
        } else {
            allAdvertisements.filter {
                it.car.brand.contains(query, ignoreCase = true) ||
                it.car.model.contains(query, ignoreCase = true) ||
                it.car.bodyType.toString().contains(query, ignoreCase = true)
            }
        }
        _uiState = uiState.copy(searchQuery = query, advertisements = filteredList)
    }

    fun loadAdvertisements() {
        launchWithErrorHandling {
            val fetchedAds: List<AdvertisementResponse> = advertisementRepository.getAllAdvertisements()
            val adsUiState = fetchedAds.map { ad ->
                AdvertisementUiState(
                    id = ad.id,
                    availableFrom = ad.availableFrom,
                    availableUntil = ad.availableUntil,
                    price = ad.price.toString(),
                    address = ad.address,
                    car = ad.car
                )
            }
            allAdvertisements = adsUiState
            _uiState = uiState.copy(advertisements = adsUiState)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
