package worklogtracker.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import worklogtracker.data.remote.ApiClient
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.UpdateTaskStatusRequest
import worklogtracker.shared.dto.task.TaskResponse

class TaskRepository(private val api: ApiClient) {
    private val baseUrl = "http://10.0.2.2:8080/api/tasks"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getTasks(): List<TaskResponse> {
        val response = api.get(baseUrl)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun createTask(request: CreateTaskRequest) {
        val body = json.encodeToString(request)
        api.post(baseUrl, body)
    }

    suspend fun updateTaskStatus(id: String, request: UpdateTaskStatusRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/$id/status", body)
    }

    suspend fun assignTask(id: String, request: AssignTaskRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/$id/assign", body)
    }

    suspend fun getUpcomingDeadlines(): List<TaskResponse> {
        val response = api.get("$baseUrl/upcoming")
        return json.decodeFromString(response.bodyAsText())
    }
}
