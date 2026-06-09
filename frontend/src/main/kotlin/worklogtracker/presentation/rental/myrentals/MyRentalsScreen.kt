package worklogtracker.presentation.rental.myrentals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.data.remote.rental.RentalResponse
import worklogtracker.data.remote.rental.RentalTripResponse
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.theme.RmcColors
import worklogtracker.presentation.rentaltrip.register.AddTripDialog

@Composable
fun MyRentalsScreen(backStack: NavBackStack<NavKey>) {
    val rentalsViewModel: MyRentalsViewModel = koinViewModel()
    val rentalsState = rentalsViewModel.uiState

    LaunchedEffect(Unit) {
        rentalsViewModel.loadRentals()
    }

    var selectedRentalId by remember { mutableStateOf<Int?>(null) }

    RmcScreen(backStack = backStack) {
        when {
            rentalsState.loading -> CircularProgressIndicator()
            rentalsState.error != null -> Text(rentalsState.error)
            rentalsState.rentals.isEmpty() -> Text("No rentals found", color = Color.White)
            else -> {
                LazyColumn {
                    items(rentalsState.rentals) { rental ->
                        RentalItem(
                            rental = rental,
                            onAddTripClick = { selectedRentalId = rental.id }
                        )
                    }
                }
            }
        }
    }

    selectedRentalId?.let { rentalId ->
        AddTripDialog(
            rentalId = rentalId,
            onDismiss = { selectedRentalId = null },
            onSuccess = {
                selectedRentalId = null
                rentalsViewModel.loadRentals()
            }
        )
    }
}

@Composable
fun RentalItem(rental: RentalResponse, onAddTripClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = RmcColors.Surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Rental #${rental.id}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Text("Status: ${rental.rentalStatus}", color = RmcColors.TextSecondary)
            Text("From: ${rental.pickUpDate}", color = RmcColors.TextSecondary)
            Text("Until: ${rental.returningDate}", color = RmcColors.TextSecondary)

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            Text(
                text = "Trips",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(4.dp))

            if (rental.rentalTrips.isEmpty()) {
                Text(
                    text = "No trips registered yet",
                    color = Color.White,
                    fontSize = 12.sp
                )
            } else {
                rental.rentalTrips.forEach {
                    TripItem(it)
                }
            }

            Spacer(Modifier.height(16.dp))

            RmcPrimaryButton(
                text = "Add Trip",
            ) {
                onAddTripClick()
            }
        }
    }
}


@Composable
fun TripItem(trip: RentalTripResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(
                RmcColors.Background,
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = "${trip.startDate} → ${trip.endDate}",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = "Mileage: ${trip.startMileage} - ${trip.endMileage}",
            color = RmcColors.TextSecondary,
            fontSize = 11.sp
        )
    }
}
