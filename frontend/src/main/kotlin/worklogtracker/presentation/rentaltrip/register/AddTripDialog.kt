package worklogtracker.presentation.rentaltrip.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.components.input.DatePickerField
import worklogtracker.presentation.framework.components.input.RmcTextField
import worklogtracker.presentation.framework.components.text.HeaderText
import worklogtracker.presentation.framework.theme.RmcColors
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AddTripDialog(
    rentalId: Int,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    val viewModel: RegisterTripViewModel = koinViewModel()
    val state = viewModel.uiState

    viewModel.onSuccess = onSuccess

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = RmcColors.Surface,
        title = {
            HeaderText("Add Trip")
        },
        text = {
            Column {
                Spacer(Modifier.height(8.dp))

                RmcTextField(
                    value = state.startMileage,
                    placeholder = "Start mileage",
                    onValueChange = viewModel.updateState { value ->
                        copy(startMileage = value)
                    }
                )

                Spacer(Modifier.height(16.dp))

                RmcTextField(
                    value = state.endMileage,
                    placeholder = "End mileage",
                    onValueChange = viewModel.updateState { value ->
                        copy(endMileage = value)
                    }
                )

                Spacer(Modifier.height(16.dp))

                DatePickerField(
                    text = state.startDate,
                    placeholder = "Start date",
                    onDateSelected = viewModel.updateState { value ->
                        copy(startDate = value)
                    },
                    dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                )

                Spacer(Modifier.height(16.dp))

                DatePickerField(
                    text = state.endDate,
                    placeholder = "End date",
                    onDateSelected = viewModel.updateState { value ->
                        copy(endDate = value)
                    },
                    dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                )

                state.error?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(it, color = RmcColors.Error)
                }
            }
        },
        confirmButton = {
            RmcPrimaryButton("Add") {
                viewModel.registerTrip(rentalId)
            }
        },
    )
}

