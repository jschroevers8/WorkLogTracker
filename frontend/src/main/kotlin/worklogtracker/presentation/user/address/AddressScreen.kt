package worklogtracker.presentation.user.address

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.components.input.RmcTextField
import worklogtracker.presentation.framework.theme.RmcColors
import worklogtracker.presentation.user.components.RmcLogoHeader

@Composable
fun AddressScreen(
    backStack: NavBackStack<NavKey>,
    previousScreen: NavKey? = null
) {
    val viewModel: AddressViewModel = koinViewModel()
    val state = viewModel.uiState

    viewModel.onAddressSaved = {
        if (previousScreen != null) {
            backStack.add(previousScreen)
        } else {
            backStack.removeLastOrNull()
        }
    }

    RmcScreen(backStack = backStack) {

        Spacer(modifier = Modifier.height(150.dp))

        RmcTextField(
            state.street,
            "Street",
            viewModel.updateState { value -> copy(street = value) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RmcTextField(
            state.houseNumber,
            "House number",
            viewModel.updateState { value -> copy(houseNumber = value.filter { it.isDigit() }) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RmcTextField(
            state.subHouseNumber ?: "",
            "Addition (optional)",
            viewModel.updateState { value -> copy(subHouseNumber = value.ifBlank { null }) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RmcTextField(
            state.postalCode,
            "Postal code",
            viewModel.updateState { value -> copy(postalCode = value) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RmcTextField(
            state.city,
            "City",
            viewModel.updateState { value -> copy(city = value) }
        )

        Spacer(modifier = Modifier.height(28.dp))

        RmcPrimaryButton(
            text = "Save address",
            loading = state.loading,
            onClick = viewModel::saveAddress
        )

        state.error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = RmcColors.Error)
        }
    }
}
