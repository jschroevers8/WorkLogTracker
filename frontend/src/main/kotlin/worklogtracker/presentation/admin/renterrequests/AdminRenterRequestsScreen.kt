package worklogtracker.presentation.admin.renterrequests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.text.HeaderText

@Composable
fun AdminRenterRequestsScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: AdminRenterRequestsViewModel = koinViewModel()
    val state = viewModel.uiState

    RmcScreen(
        backStack = backStack,
    ) {
        HeaderText("Renter aanvragen")

        Spacer(Modifier.height(16.dp))

        if (state.loading) {
            CircularProgressIndicator()
            return@RmcScreen
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF2C2C2C), RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            state.requests.forEach { request ->
                AdminRenterRequestRow(
                    request = request,
                    onAccept = { viewModel.accept(request.id) },
                    onReject = { }
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        state.error?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = Color.Red)
        }
    }
}
