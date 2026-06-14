package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.webapp.ApiClient

class EmployeeDetailViewModel(
    private val api: ApiClient,
) {
    var worklogs by mutableStateOf<List<WorkLogResponse>>(emptyList())
    var loading by mutableStateOf(true)
    var error by mutableStateOf("")

    suspend fun loadWorkLogs(userId: Long) {
        loading = true
        error = ""
        try {
            worklogs = api.worklogs.getUserWorkLogs(userId)
        } catch (e: Exception) {
            error = "Fout bij ophalen uren: ${e.message}"
        } finally {
            loading = false
        }
    }
}
