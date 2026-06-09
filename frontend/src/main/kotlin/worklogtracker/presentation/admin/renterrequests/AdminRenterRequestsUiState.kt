package worklogtracker.presentation.admin.renterrequests

import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class AdminRenterRequestsUiState(
    val requests: List<RenterRequestUiState> = emptyList(),

    override val loading: Boolean = false,
    override val error: String? = null
): BaseUiState
