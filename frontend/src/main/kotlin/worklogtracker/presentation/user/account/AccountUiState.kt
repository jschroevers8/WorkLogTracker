package worklogtracker.presentation.user.account

import worklogtracker.data.remote.user.UserType
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class AccountUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val userType: UserType? = null,
    val userPoints: Int = 0,
    val renterRightsRequested: Boolean = false,
    override val loading: Boolean = false,
    override val error: String? = null
): BaseUiState


