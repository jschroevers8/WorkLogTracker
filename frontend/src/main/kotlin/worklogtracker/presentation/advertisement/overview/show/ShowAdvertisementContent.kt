package worklogtracker.presentation.advertisement.overview.show

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import worklogtracker.R
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.advertisement.overview.general.uistates.AdvertisementUiState
import worklogtracker.presentation.advertisement.overview.show.map.MapScreen
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.datetime.formatDate

@Composable
fun ShowAdvertisementContent(
    backStack: NavBackStack<NavKey>,
    advertisement: AdvertisementUiState,
    isLoggedIn: Boolean
) {
    val context = LocalContext.current;

    RmcScreen(backStack = backStack) {
        MapScreen(
            address = advertisement.address,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (advertisement.car.carImages.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(advertisement.car.carImages) { carImage ->
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(carImage.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Car image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(200.dp)
                                .padding(8.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                ),
                            placeholder = painterResource(R.drawable.no_car_image),
                            error = painterResource(R.drawable.no_car_image),
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.no_car_image),
                    contentDescription = "No image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "${ advertisement.car.brand} ${advertisement.car.model}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "€${advertisement.price}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Beschikbaar van ${formatDate(advertisement.availableFrom)} tot ${formatDate(advertisement.availableUntil)}",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (isLoggedIn) {
                    RmcPrimaryButton("Selecteer / Reserveer", onClick = { backStack.add(Screen.CreateReservation(advertisement.id))  })
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
