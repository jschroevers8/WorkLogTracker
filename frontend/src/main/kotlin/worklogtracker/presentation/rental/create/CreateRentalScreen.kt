package worklogtracker.presentation.rental.create

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.input.DatePickerField
import worklogtracker.presentation.framework.components.text.HeaderText
import worklogtracker.presentation.framework.theme.RmcColors
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CreateReservationScreen(backStack: NavBackStack<NavKey>, advertisementId: Int) {
    val viewModel: CreateRentalViewModel = koinViewModel()
    val state = viewModel.uiState

    viewModel.onSuccess = {
        backStack.add(Screen.AvailableAdvertisements)
    }

    RmcScreen(backStack = backStack) {
        HeaderText("Create Reservation")

        Spacer(Modifier.height(24.dp))

        Spacer(Modifier.height(16.dp))

        DatePickerField(
            text = state.pickUpDate,
            placeholder = "Select Available From",
            onDateSelected = viewModel.updateState { value -> copy(pickUpDate = value) },
            dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        )

        Spacer(Modifier.height(16.dp))

        DatePickerField(
            text = state.returningDate,
            placeholder = "Select Returning Date",
            onDateSelected = viewModel.updateState { value -> copy(returningDate = value) },
            dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        )

        Spacer(Modifier.height(32.dp))

        RmcPrimaryButton("Create Reservation") {
            viewModel.createReservation(advertisementId)
        }

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = RmcColors.Error)
        }
    }
}
