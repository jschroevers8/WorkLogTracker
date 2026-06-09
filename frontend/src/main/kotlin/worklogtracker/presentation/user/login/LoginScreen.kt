package worklogtracker.presentation.user.login

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.navigation.Screen

@Composable
fun LoginScreen(backStack: NavBackStack<NavKey>, previousScreen: NavKey?) {
    val viewModel: LoginViewModel = koinViewModel()
    val state = viewModel.uiState

    viewModel.onLoginSuccess = {
        backStack.add(Screen.Account)
    }

    LoginContent(
        state = state,
        onEmailChange = viewModel.updateState { copy(email = it) },
        onPasswordChange = viewModel.updateState { copy(password = it) },
        onLoginClick = viewModel::login,
        onSignupClick = { backStack.add(Screen.Signup(previousScreen as Screen?)) },
        backStack = backStack
    )
}
