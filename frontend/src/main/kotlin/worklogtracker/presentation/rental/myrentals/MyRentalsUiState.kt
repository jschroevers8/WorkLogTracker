package worklogtracker.presentation.rental.myrentals

import worklogtracker.data.remote.rental.RentalResponse
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class MyRentalsUiState(
    val rentals: List<RentalResponse> = emptyList(),

    override val loading: Boolean = false,
    override val error: String? = null
): BaseUiState
