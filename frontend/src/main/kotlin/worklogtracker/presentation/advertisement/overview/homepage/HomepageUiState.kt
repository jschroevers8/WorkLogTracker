package worklogtracker.presentation.advertisement.overview.homepage

import worklogtracker.presentation.advertisement.overview.general.uistates.AdvertisementUiState
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class HomepageUiState(
    val advertisements: List<AdvertisementUiState> = emptyList(),
    val searchQuery: String = "",
    override val loading: Boolean = true,
    override val error: String? = null
) : BaseUiState
