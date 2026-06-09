package worklogtracker.presentation.car.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.framework.BottomNavigationBar

@Composable
fun PersonalCarsScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: PersonalCarsViewModel = koinViewModel()
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadPersonalCars()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(backStack = backStack) { selectedScreen ->
                backStack.add(selectedScreen)
            }
        },
        containerColor = Color(0xFF1F1F1F)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1F1F))
                .padding(padding)
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                "My Cars",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            when {
                state.loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    Text(
                        "Error: ${state.error}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        content = {
                            items(state.cars) { car ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable{ backStack.add(Screen.EditPersonalCar(car.id!!)) }
                                        .background(Color(0xFF2C2C2C), RoundedCornerShape(14.dp)),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text("Merk: ${car.brand}", color = Color.White)
                                        Text("Model: ${car.model}", color = Color.White)
                                        Text("Bouwjaar: ${car.modelYear}", color = Color.White)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
