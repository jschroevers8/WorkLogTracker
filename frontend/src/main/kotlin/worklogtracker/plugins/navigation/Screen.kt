package worklogtracker.plugins.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val requiresAuth: Boolean = false) : NavKey {

    @Serializable
    @SerialName("login")
    data class Login(val targetScreen: Screen? = null) : Screen(requiresAuth = false)

    @Serializable
    @SerialName("signup")
    data class Signup(val targetScreen: Screen? = null) : Screen(requiresAuth = false)

    @Serializable
    @SerialName("homepage")
    object Homepage : Screen()

    @Serializable
    @SerialName("available_advertisement")
    object AvailableAdvertisements : Screen()

    @Serializable
    @SerialName("notification")
    object Notification : Screen(requiresAuth = true)

    @Serializable
    @SerialName("account")
    object Account : Screen(requiresAuth = true)

    @Serializable
    @SerialName("address")
    object Address : Screen(requiresAuth = true)

    @Serializable
    @SerialName("create_dashboard")
    object CreateDashboard : Screen(requiresAuth = true)

    @Serializable
    @SerialName("personalCars")
    object PersonalCars : Screen(requiresAuth = true)

    @Serializable
    @SerialName("create_car")
    object CreateCar : Screen(requiresAuth = true)

    @Serializable
    @SerialName("create_advertisement")
    object Advertisement : Screen(requiresAuth = true)

    @Serializable
    @SerialName("show_advertisement")
    data class ShowAdvertisement(val advertisementId: Int) : Screen()

    @Serializable
    @SerialName("edit_advertisement")
    data class EditPersonalCar(val carId: Int) : Screen()

    @SerialName("admin_renter_requests")
    object AdminRenterRequests : Screen(requiresAuth = true)

    @Serializable
    @SerialName("create_reservation")
    data class CreateReservation(val advertisementId: Int) : Screen()

    @Serializable
    @SerialName("my_rentals")
    object MyRentals : Screen(requiresAuth = true)

    @Serializable
    @SerialName("recommended_car")
    object RecommendedCar : Screen(requiresAuth = true)
}
