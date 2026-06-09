package worklogtracker.presentation.car.personal.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.koin.androidx.compose.koinViewModel
import worklogtracker.R
import worklogtracker.data.remote.car.BodyType
import worklogtracker.data.remote.car.FuelType
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.framework.camera.rememberCameraController
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.input.RmcTextField
import worklogtracker.presentation.framework.components.input.SelectDropdownField
import worklogtracker.presentation.framework.components.text.HeaderText
import worklogtracker.presentation.framework.theme.RmcColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPersonalCarScreen(
    backStack: NavBackStack<NavKey>,
    carId: Int
) {
    val viewModel: EditPersonalCarsViewModel = koinViewModel()
    val state = viewModel.uiState
    val context = LocalContext.current;

    LaunchedEffect(carId) {
        viewModel.loadCar(carId)
    }

    viewModel.onSuccess = {
        backStack.add(Screen.AvailableAdvertisements)
    }

    val openCamera = rememberCameraController { uri ->
        viewModel.addCarImage(uri.toString())
    }

    RmcScreen(backStack = backStack) {
        HeaderText("Edit your Car")
        Spacer(Modifier.height(24.dp))

        RmcTextField(
            value = state.brand,
            placeholder = "Brand",
            onValueChange = viewModel.updateState { copy(brand = it) }
        )

        Spacer(Modifier.height(16.dp))

        RmcTextField(
            value = state.model,
            placeholder = "Model",
            onValueChange = viewModel.updateState { copy(model = it) }
        )

        Spacer(Modifier.height(16.dp))

        RmcTextField(
            value = state.modelYear,
            placeholder = "Model Year",
            onValueChange = viewModel.updateState { copy(modelYear = it) }
        )

        Spacer(Modifier.height(16.dp))

        RmcTextField(
            value = state.licensePlate,
            placeholder = "License Plate",
            onValueChange = viewModel.updateState { copy(licensePlate = it) }
        )

        Spacer(Modifier.height(16.dp))

        RmcTextField(
            value = state.mileage,
            placeholder = "Mileage",
            onValueChange = viewModel.updateState { copy(mileage = it) }
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SelectDropdownField(
                value = state.fuelType,
                placeholder = "Fuel type",
                options = FuelType.entries,
                modifier = Modifier.weight(1f),
                onSelect = viewModel.updateState { value -> copy(fuelType = value) }
            )

            SelectDropdownField(
                value = state.bodyType,
                placeholder = "Body Type",
                options = BodyType.entries,
                modifier = Modifier.weight(1f),
                onSelect = viewModel.updateState { value -> copy(bodyType = value) }
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = openCamera,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(55.dp)
            ) { Text("Take Photo", color = Color.White) }
        }

        Spacer(Modifier.height(16.dp))

        if (state.carImages.isNotEmpty()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.carImages) { carImage ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(carImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Car image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 8.dp),
                        placeholder = painterResource(R.drawable.no_car_image),
                        error = painterResource(R.drawable.no_car_image)
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        RmcPrimaryButton("Save Changes") {
            viewModel.updateCar(carId)
        }

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = RmcColors.Error)
        }
    }
}
