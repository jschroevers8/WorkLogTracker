package worklogtracker.presentation.rental.create

import worklogtracker.presentation.framework.annotations.Required
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class CreateRentalUiState(
    @Required("Pick up date must be selected")
    val pickUpDate: String = "",

    @Required("Return date must be selected")
    val returningDate: String = "",

    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState
