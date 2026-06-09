package worklogtracker.presentation.advertisement.overview.show

import worklogtracker.presentation.advertisement.overview.general.uistates.AdvertisementUiState
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class ShowAdvertisementUiState(
    val advertisement: AdvertisementUiState? = null,
    val isLoggedIn: Boolean = false,
    override val loading: Boolean = true,
    override val error: String? = null
) : BaseUiState
