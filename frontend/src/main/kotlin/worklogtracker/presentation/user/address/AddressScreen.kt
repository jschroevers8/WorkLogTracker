package worklogtracker.presentation.user.address

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.presentation.framework.components.WltScreen
import worklogtracker.presentation.framework.components.button.WltPrimaryButton
import worklogtracker.presentation.framework.components.input.WltTextField
import worklogtracker.presentation.framework.theme.WltColors
import worklogtracker.presentation.user.components.WltLogoHeader

@Composable
fun AddressScreen(
    backStack: NavBackStack<NavKey>,
    previousScreen: NavKey? = null
) {
    val viewModel: AddressViewModel = koinViewModel()

    viewModel.onAddressSaved = {
        if (previousScreen != null) {
            backStack.add(previousScreen)
        } else {
            backStack.removeLastOrNull()
        }
    }

    AddressScreenContent(
        state = viewModel.uiState,
        onStreetChange = viewModel.updateState { copy(street = it) },
        onHouseNumberChange = viewModel.updateState { copy(houseNumber = it.filter { char -> char.isDigit() }) },
        onSubHouseNumberChange = viewModel.updateState { copy(subHouseNumber = it.ifBlank { null }) },
        onPostalCodeChange = viewModel.updateState { copy(postalCode = it) },
        onCityChange = viewModel.updateState { copy(city = it) },
        onSaveAddress = viewModel::saveAddress,
        backStack = backStack
    )
}

@Composable
fun AddressScreenContent(
    state: AddressUiState,
    onStreetChange: (String) -> Unit,
    onHouseNumberChange: (String) -> Unit,
    onSubHouseNumberChange: (String) -> Unit,
    onPostalCodeChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onSaveAddress: () -> Unit,
    backStack: NavBackStack<NavKey>
) {
    WltScreen(backStack = backStack) {

        Spacer(modifier = Modifier.height(150.dp))

        WltTextField(
            state.street,
            "Street",
            onStreetChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        WltTextField(
            state.houseNumber,
            "House number",
            onHouseNumberChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        WltTextField(
            state.subHouseNumber ?: "",
            "Addition (optional)",
            onSubHouseNumberChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        WltTextField(
            state.postalCode,
            "Postal code",
            onPostalCodeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        WltTextField(
            state.city,
            "City",
            onCityChange
        )

        Spacer(modifier = Modifier.height(28.dp))

        WltPrimaryButton(
            text = "Save address",
            loading = state.loading,
            onClick = onSaveAddress
        )

        state.error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = WltColors.Error)
        }
    }
}
