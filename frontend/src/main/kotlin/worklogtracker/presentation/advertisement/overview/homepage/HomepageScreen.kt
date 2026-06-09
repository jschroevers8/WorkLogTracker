package worklogtracker.presentation.advertisement.overview.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
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
import worklogtracker.presentation.advertisement.overview.homepage.components.BannerCar
import worklogtracker.presentation.framework.BottomNavigationBar
import worklogtracker.presentation.advertisement.overview.general.components.CarCard
import worklogtracker.presentation.advertisement.overview.homepage.components.CategoriesRow
import worklogtracker.presentation.advertisement.overview.general.components.FilterChip

@Composable
fun HomePageScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: HomepageViewModel = koinViewModel()
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadAdvertisements()
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
                .padding(padding)
                .background(Color(0xFF1F1F1F))
        ) {
            Spacer(Modifier.height(20.dp))

            TextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2C2C),
                    unfocusedContainerColor = Color(0xFF2C2C2C),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            )

            Row(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip("Favorites")
                FilterChip("For you")
                FilterChip("Orders")
            }

            Spacer(Modifier.height(10.dp))
            BannerCar()
            Spacer(Modifier.height(10.dp))
            CategoriesRow(onCategoryClick = { category -> viewModel.onSearchQueryChanged(category) })
            Spacer(Modifier.height(30.dp))

            Text(
                "Available Cars",
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
                            items(state.advertisements) { advertisement ->
                                CarCard(advertisement, backStack)
                            }
                        }
                    )
                }
            }
        }
    }
}
