package worklogtracker.webapp.repositories

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest

class WorkLogRepository(private val client: HttpClient, private val baseUrl: String, private val getToken: () -> String?) {
    suspend fun getUserWorkLogs(userId: Int, taskAssignmentId: Int? = null): List<WorkLogResponse> {
        return client.get("$baseUrl/worklogs") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            parameter("userId", userId)
            if (taskAssignmentId != null) {
                parameter("taskAssignmentId", taskAssignmentId)
            }
        }.body()
    }
}
