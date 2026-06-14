package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import worklogtracker.webapp.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.coroutineScope
import worklogtracker.shared.dto.worklog.WorkLogResponse
import kotlin.js.Date

class DashboardViewModel(private val apiClient: ApiClient) {
    var totalEmployees by mutableStateOf(0)
    var activeProjects by mutableStateOf(0)
    var totalHoursThisWeek by mutableStateOf(0.0)
    var dailyHours by mutableStateOf<List<Pair<String, Double>>>(emptyList())
    var isLoading by mutableStateOf(false)

    suspend fun startPeriodicRefresh() = coroutineScope {
        while (isActive) {
            loadDashboardData()
            delay(10000) // Elke 10 seconden verversen
        }
    }

    suspend fun loadDashboardData() {
        try {
            if (!isLoading && totalEmployees == 0) isLoading = true
            
            val users = apiClient.users.getUsers()
            totalEmployees = users.size

            val projects = apiClient.projects.getProjects(status = "ACTIVE")
            activeProjects = projects.size

            // Haal worklogs op voor alle gebruikers
            val allWorklogs = mutableListOf<WorkLogResponse>()
            users.forEach { user ->
                try {
                    val userLogs = apiClient.worklogs.getUserWorkLogs(user.id)
                    allWorklogs.addAll(userLogs)
                } catch (e: Exception) {
                    console.error("Fout bij ophalen worklogs voor gebruiker ${user.id}:", e)
                }
            }

            totalHoursThisWeek = allWorklogs.sumOf { it.hours }

            // Bereken uren per dag (laatste 7 dagen)
            val days = listOf("Zo", "Ma", "Di", "Wo", "Do", "Vr", "Za")
            val today = Date()
            val last7Days = (0..6).map { i ->
                val d = Date(today.getTime() - (i * 24 * 60 * 60 * 1000))
                d.getDay().toInt() to d.toLocaleDateString()
            }.reversed()

            dailyHours = last7Days.map { (dayIdx, dateString) ->
                val dayName = days[dayIdx]
                val hours = allWorklogs.filter { 
                    try {
                        val logDate = Date(it.createdAt)
                        val logDateString = logDate.toLocaleDateString()
                        
                        // Fallback check voor ISO strings die toLocaleDateString anders kunnen formatteren
                        val isoDatePart = it.createdAt.split("T")[0]
                        val targetIsoPart = dateString.split("/").let { parts ->
                            if (parts.size == 3) {
                                // Verwacht DD/MM/YYYY of MM/DD/YYYY, probeer te matchen
                                "${parts[2]}-${parts[1].padStart(2, '0')}-${parts[0].padStart(2, '0')}"
                            } else dateString
                        }
                        
                        logDateString == dateString || isoDatePart == targetIsoPart
                    } catch (e: Exception) {
                        false
                    }
                }.sumOf { it.hours }
                dayName to hours
            }
            
            isLoading = false
        } catch (e: Exception) {
            console.error("Fout bij ophalen dashboard data:", e)
            isLoading = false
        }
    }
}
