package worklogtracker.presentation.user.login

import worklogtracker.presentation.framework.annotations.Email
import worklogtracker.presentation.framework.annotations.Required
import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class LoginUiState(
    @Required("Email field cannot be empty")
    @Email("Please enter a valid email")
    val email: String = "",

    @Required("Password field cannot be empty")
    val password: String = "",

    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState
