package worklogtracker.webapp.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    val requiresAuth: Boolean = true,
) {
    @Serializable
    @SerialName("login")
    object Login : Screen(requiresAuth = false)

    @Serializable
    @SerialName("dashboard")
    object Dashboard : Screen(requiresAuth = true)

    @Serializable
    @SerialName("employees")
    object Employees : Screen(requiresAuth = true)

    @Serializable
    @SerialName("projects")
    object Projects : Screen(requiresAuth = true)

    @Serializable
    @SerialName("employee_detail")
    data class EmployeeDetail(
        val userId: Long,
    ) : Screen(requiresAuth = true)

    @Serializable
    @SerialName("project_detail")
    data class ProjectDetail(
        val projectId: Int,
    ) : Screen(requiresAuth = true)
}
