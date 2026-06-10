package worklogtracker.presentation.user.account

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel

import worklogtracker.navigation.Screen

@Composable
fun AccountScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: AccountViewModel = koinViewModel()

    viewModel.onLogoutSuccess = {
        backStack.add(Screen.Projects)
    }

    AccountScreenContent(
        state = viewModel.uiState,
        onLoadUser = viewModel::loadUser,
        onLogout = viewModel::logout,
        backStack = backStack
    )
}