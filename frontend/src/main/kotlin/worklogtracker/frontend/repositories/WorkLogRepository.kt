package worklogtracker.frontend.repositories

import kotlinx.serialization.json.Json
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest

class WorkLogRepository(
    private val api: ApiClient,
) {
    private val baseUrl = "http://10.0.2.2:8080/api"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun createWorkLog(request: CreateWorkLogRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/time-entries", body)
    }
}
