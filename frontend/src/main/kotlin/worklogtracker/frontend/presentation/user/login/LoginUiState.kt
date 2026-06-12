package worklogtracker.frontend.presentation.user.login

import worklogtracker.frontend.presentation.framework.annotations.Email
import worklogtracker.frontend.presentation.framework.annotations.Required
import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState

data class LoginUiState(
    @Required("Email field cannot be empty")
    @Email("Please enter a valid email")
    val email: String = "",

    @Required("Password field cannot be empty")
    val password: String = "",

    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState
