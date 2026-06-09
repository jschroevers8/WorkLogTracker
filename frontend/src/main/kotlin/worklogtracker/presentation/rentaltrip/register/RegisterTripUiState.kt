package worklogtracker.presentation.rentaltrip.register

import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class RegisterTripUiState(
    val startMileage: String = "",
    val endMileage: String = "",
    val startDate: String = "",
    val endDate: String = "",

    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState
