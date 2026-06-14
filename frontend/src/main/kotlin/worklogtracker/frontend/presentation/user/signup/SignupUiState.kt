package worklogtracker.frontend.presentation.user.signup

import worklogtracker.frontend.presentation.framework.annotations.Email
import worklogtracker.frontend.presentation.framework.annotations.Required
import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState

data class SignupUiState(
    @Required("Firstname field cannot be empty")
    val firstName: String = "",
    @Required("Lastname field cannot be empty")
    val lastName: String = "",
    @Required("Email field cannot be empty")
    @Email("Please enter a valid email")
    val email: String = "",
    @Required("PhoneNumber field cannot be empty")
    val phoneNumber: String = "",
    @Required("Password field cannot be empty")
    val password: String = "",
    override val loading: Boolean = false,
    override val error: String? = null,
) : BaseUiState
