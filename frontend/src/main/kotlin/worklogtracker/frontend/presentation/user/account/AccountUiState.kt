package worklogtracker.frontend.presentation.user.account

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState

data class AccountUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val userPoints: Int = 0,
    val renterRightsRequested: Boolean = false,
    override val loading: Boolean = false,
    override val error: String? = null
): BaseUiState


