package worklogtracker.frontend.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import worklogtracker.shared.dto.worklog.WorkLogResponse

class WorkLogRepository(private val api: ApiClient) {
    private val baseUrl = "http://10.0.2.2:8080/api"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getWorkLogs(): List<WorkLogResponse> {
        val response = api.get("$baseUrl/worklogs")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun createWorkLog(request: CreateWorkLogRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/time-entries", body)
    }
}
