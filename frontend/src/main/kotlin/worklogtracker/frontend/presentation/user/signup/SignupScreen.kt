package worklogtracker.frontend.presentation.user.signup

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.frontend.navigation.Screen

@Composable
fun SignupScreen(
    backStack: NavBackStack<NavKey>,
    previousScreen: NavKey?,
) {
    val viewModel: SignupViewModel = koinViewModel()
    val state = viewModel.uiState

    viewModel.onSuccess = {
        if (previousScreen != null) {
            backStack.add(previousScreen)
        }
    }

    SignupContent(
        state = state,
        onFirstNameChange = viewModel.updateState { value -> copy(firstName = value) },
        onLastNameChange = viewModel.updateState { value -> copy(lastName = value) },
        onEmailChange = viewModel.updateState { value -> copy(email = value) },
        onPasswordChange = viewModel.updateState { value -> copy(password = value) },
        onSignupClick = viewModel::signup,
        onLoginClick = {
            backStack.add(Screen.Login(previousScreen as Screen?))
        },
        backStack = backStack,
    )
}
