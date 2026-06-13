package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import worklogtracker.webapp.ApiClient

class DashboardViewModel(private val apiClient: ApiClient) {
    var totalEmployees by mutableStateOf(0)
    var activeProjects by mutableStateOf(0)
    var totalHoursThisWeek by mutableStateOf(0.0)

    suspend fun loadDashboardData() {
        try {
            val users = apiClient.users.getUsers()
            totalEmployees = users.size

            val projects = apiClient.projects.getProjects(status = "ACTIVE")
            activeProjects = projects.size

            // Voor nu halen we de uren van de huidige gebruiker op.
            // In een echt admin dashboard zou hier een specifiek endpoint voor zijn.
            val worklogs = apiClient.worklogs.getUserWorkLogs(0)
            totalHoursThisWeek = worklogs.sumOf { it.hours }
        } catch (e: Exception) {
            console.error("Fout bij ophalen dashboard data:", e)
        }
    }
}
