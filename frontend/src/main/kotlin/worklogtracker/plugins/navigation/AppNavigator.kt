package worklogtracker.plugins.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import worklogtracker.data.local.AuthManager
import worklogtracker.presentation.admin.renterrequests.AdminRenterRequestsScreen
import worklogtracker.presentation.user.account.AccountScreen
import worklogtracker.presentation.advertisement.overview.AvailableAdvertisementsScreen
import worklogtracker.presentation.advertisement.create.CreateAdvertisementScreen
import worklogtracker.presentation.car.create.CreateCarScreen
import worklogtracker.presentation.car.recommended.RecommendedCarScreen
import worklogtracker.presentation.dashboard.create.CreateDashboardScreen
import worklogtracker.presentation.advertisement.overview.homepage.HomePageScreen
import worklogtracker.presentation.user.notification.NotificationScreen
import worklogtracker.presentation.user.login.LoginScreen
import worklogtracker.presentation.advertisement.overview.show.ShowAdvertisementScreen
import worklogtracker.presentation.rental.create.CreateReservationScreen
import worklogtracker.presentation.rental.myrentals.MyRentalsScreen
import worklogtracker.presentation.car.personal.edit.EditPersonalCarScreen
import worklogtracker.presentation.car.personal.PersonalCarsScreen
import worklogtracker.presentation.user.address.AddressScreen
import worklogtracker.presentation.user.signup.SignupScreen
import worklogtracker.plugins.navigation.Screen

@Composable
fun AppNavigator() {
    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }
    val backStack = rememberNavBackStack(Screen.Homepage)

    val token by authManager.authTokenFlow.collectAsState(initial = null)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider@{ navKey ->
            val screen = navKey as? Screen
                ?: return@entryProvider NavEntry(navKey) { }
            NavEntry(screen) {

                if (screen.requiresAuth && token == null) {
                    LaunchedEffect(screen) {
                        backStack.add(Screen.Login(screen))
                    }

                    return@NavEntry
                }

                when (screen) {
                    is Screen.Login -> LoginScreen(backStack,screen.targetScreen)
                    is Screen.Signup -> SignupScreen(backStack, screen.targetScreen)
                    is Screen.Homepage -> HomePageScreen(backStack)
                    is Screen.AvailableAdvertisements -> AvailableAdvertisementsScreen(backStack)
                    is Screen.ShowAdvertisement -> ShowAdvertisementScreen(backStack, screen.advertisementId)
                    is Screen.CreateCar -> CreateCarScreen(backStack)
                    is Screen.PersonalCars -> PersonalCarsScreen(backStack)
                    is Screen.Account -> AccountScreen(backStack)
                    is Screen.Address -> AddressScreen(backStack)
                    is Screen.Notification -> NotificationScreen(backStack)
                    is Screen.CreateDashboard -> CreateDashboardScreen(backStack)
                    is Screen.Advertisement -> CreateAdvertisementScreen(backStack)
                    is Screen.EditPersonalCar -> EditPersonalCarScreen(backStack, screen.carId)
                    is Screen.AdminRenterRequests -> AdminRenterRequestsScreen(backStack)
                    is Screen.CreateReservation -> CreateReservationScreen(backStack, screen.advertisementId)
                    is Screen.MyRentals -> MyRentalsScreen(backStack)
                    is Screen.RecommendedCar -> RecommendedCarScreen(backStack)
                }
            }
        }
    )
}
