package worklogtracker.presentation.user.account

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: AccountViewModel = koinViewModel()
    AccountScreenContent(viewModel = viewModel, backStack = backStack)
}