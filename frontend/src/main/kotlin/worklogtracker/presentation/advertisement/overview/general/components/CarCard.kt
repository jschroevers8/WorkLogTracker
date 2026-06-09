package worklogtracker.presentation.advertisement.overview.general.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.core.net.toUri
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import worklogtracker.R
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.advertisement.overview.general.uistates.AdvertisementUiState

@Composable
fun CarCard(ad: AdvertisementUiState, backStack: NavBackStack<NavKey>) {
    val context = LocalContext.current;

    Card(
        modifier = Modifier
            .width(182.dp)
            .clickable {
                backStack.add(Screen.ShowAdvertisement(ad.id))
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            if (! ad.car.carImages.isEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(ad.car.carImages[0].image.toUri())
                        .crossfade(true)
                        .build(),
                    contentDescription = "Car image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    placeholder = painterResource(R.drawable.no_car_image),
                    error = painterResource(R.drawable.no_car_image)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.no_car_image),
                    contentDescription = "No image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2C2C2C))
                    .padding(8.dp)
            ) {
                Text(
                    text = "${ad.car.brand} ${ad.car.model} ${ad.car.modelYear}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "$${ad.price} / day",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }
}

