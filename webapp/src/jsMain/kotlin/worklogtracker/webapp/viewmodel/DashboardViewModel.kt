package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import worklogtracker.webapp.ApiClient

class DashboardViewModel(private val apiClient: ApiClient) {
    var totalEmployees by mutableStateOf(0)
    var activeProjects by mutableStateOf(0)
    var totalHoursThisWeek by mutableStateOf(0.0)
    var dailyHours by mutableStateOf<List<Pair<String, Double>>>(emptyList())

    suspend fun loadDashboardData() {
        try {
            val users = apiClient.users.getUsers()
            totalEmployees = users.size

            val projects = apiClient.projects.getProjects(status = "ACTIVE")
            activeProjects = projects.size

            // Voor nu halen we de uren van alle gebruikers op (indien mogelijk)
            // Maar we simuleren hier data voor de grafiek op basis van de logs
            val worklogs = apiClient.worklogs.getUserWorkLogs(0)
            totalHoursThisWeek = worklogs.sumOf { it.hours }

            // Simuleer daily hours voor de grafiek (laatste 7 dagen)
            val days = listOf("Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo")
            dailyHours = days.map { day -> 
                day to (4.0 + (kotlin.random.Random.nextDouble() * 8.0)) 
            }
        } catch (e: Exception) {
            console.error("Fout bij ophalen dashboard data:", e)
        }
    }
}
