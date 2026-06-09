package worklogtracker.presentation.user.address

import worklogtracker.presentation.framework.annotations.Required
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class AddressUiState(

    @Required("Street field cannot be empty")
    val street: String = "",

    @Required("House number field cannot be empty")
    val houseNumber: String = "",

    val subHouseNumber: String? = null,

    @Required("Postal code field cannot be empty")
    val postalCode: String = "",

    @Required("City field cannot be empty")
    val city: String = "",

    override val loading: Boolean = false,
    override val error: String? = null

) : BaseUiState
