package worklogtracker.presentation.car.personal

import worklogtracker.data.remote.car.CarResponse
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class PersonalCarsUiState(
    val cars: List<CarResponse> = emptyList(),
    override val loading: Boolean = true,
    override val error: String? = null
) : BaseUiState
