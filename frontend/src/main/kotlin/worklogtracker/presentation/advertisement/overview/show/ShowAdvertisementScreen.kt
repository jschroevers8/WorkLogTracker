package worklogtracker.presentation.advertisement.overview.show

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowAdvertisementScreen(
    backStack: NavBackStack<NavKey>,
    advertisementId: Int,
) {
    val viewModel: ShowAdvertisementViewModel = koinViewModel()
    val state = viewModel.uiState

    LaunchedEffect(advertisementId) {
        viewModel.loadAdvertisement(advertisementId)
    }

    when {
        state.loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Text(
                text = state.error,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        state.advertisement != null -> {
            ShowAdvertisementContent(
                backStack = backStack,
                advertisement = state.advertisement,
                isLoggedIn = state.isLoggedIn
            )
        }
    }
}
