package worklogtracker.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import worklogtracker.data.remote.ApiClient
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import worklogtracker.shared.dto.worklog.StartTimerRequest
import worklogtracker.shared.dto.worklog.StopTimerRequest

class WorkLogRepository(private val api: ApiClient) {
    private val baseUrl = "http://10.0.2.2:8080/api/worklogs"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getWorkLogs(): String {
        val response = api.get(baseUrl)
        return response.bodyAsText()
    }

    suspend fun createWorkLog(request: CreateWorkLogRequest) {
        val body = json.encodeToString(request)
        api.post(baseUrl, body)
    }

    suspend fun startTimer(request: StartTimerRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/timer/start", body)
    }

    suspend fun stopTimer(request: StopTimerRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/timer/stop", body)
    }
}
