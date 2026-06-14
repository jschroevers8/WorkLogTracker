package worklogtracker.frontend.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    val requiresAuth: Boolean = false,
) : NavKey {
    @Serializable
    @SerialName("login")
    data class Login(
        val targetScreen: Screen? = null,
    ) : Screen(requiresAuth = false)

    @Serializable
    @SerialName("signup")
    data class Signup(
        val targetScreen: Screen? = null,
    ) : Screen(requiresAuth = false)

    @Serializable
    @SerialName("projects")
    object Projects : Screen(requiresAuth = true)

    @Serializable
    @SerialName("tasks")
    object Tasks : Screen(requiresAuth = true)

    @Serializable
    @SerialName("worklogs")
    data class WorkLogs(
        val taskId: String? = null,
    ) : Screen(requiresAuth = false)

    @Serializable
    @SerialName("notification")
    object Notification : Screen(requiresAuth = true)

    @Serializable
    @SerialName("account")
    object Account : Screen(requiresAuth = true)
}
