package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.webapp.ApiClient

class EmployeesViewModel(
    private val api: ApiClient,
) {
    var users by mutableStateOf<List<UserResponse>>(emptyList())
    var loading by mutableStateOf(true)
    var error by mutableStateOf("")

    suspend fun loadUsers() {
        loading = true
        error = ""
        try {
            users = api.users.getUsers()
        } catch (e: Exception) {
            error = "Fout bij ophalen medewerkers: ${e.message}"
        } finally {
            loading = false
        }
    }

    fun clearError() {
        error = ""
    }
}
