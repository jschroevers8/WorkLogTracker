package worklogtracker.presentation.advertisement.create

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
import worklogtracker.presentation.framework.components.input.RmcTextField
import worklogtracker.presentation.framework.components.input.SelectDropdownField
import worklogtracker.presentation.framework.components.text.HeaderText
import worklogtracker.presentation.framework.theme.RmcColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAdvertisementScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: CreateAdvertisementViewModel = koinViewModel()
    val state = viewModel.uiState

    viewModel.onSuccess = {
        backStack.add(Screen.AvailableAdvertisements)
    }

    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
    }

    RmcScreen(backStack = backStack) {
        HeaderText("Create Advertisement")

        Spacer(Modifier.height(24.dp))

        SelectDropdownField(
            value = state.cars.find { it.id.toString() == state.carId },
            placeholder = "Select car",
            options = state.cars,
            labelMapper = { car -> "${car.brand} ${car.model}" },
            onSelect = viewModel.updateState { car -> copy(carId = car.id.toString()) }
        )

        Spacer(Modifier.height(16.dp))

        SelectDropdownField(
            value = state.addresses.find { it.id.toString() == state.addressId },
            placeholder = "Select address",
            options = state.addresses,
            labelMapper = { address -> "${address.street} ${address.houseNumber}" },
            onSelect = viewModel.updateState { address -> copy(addressId = address.id.toString()) }
        )

        Spacer(Modifier.height(16.dp))

        DatePickerField(
            text = state.availableFrom,
            placeholder = "Select Available From",
            onDateSelected = viewModel.updateState { value -> copy(availableFrom = value) },
            dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        )

        Spacer(Modifier.height(16.dp))

        DatePickerField(
            text = state.availableUntil,
            placeholder = "Select Available Until",
            onDateSelected = viewModel.updateState { value -> copy(availableUntil = value) },
            dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        )

        Spacer(Modifier.height(16.dp))

        RmcTextField(state.price, "Price", viewModel.updateState { value -> copy(price = value) })

        Spacer(Modifier.height(32.dp))

        RmcPrimaryButton("Create Advertisement") {
            viewModel.createAdvertisement()
        }

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = RmcColors.Error)
        }
    }
}
