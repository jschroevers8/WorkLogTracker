package worklogtracker.frontend.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.UpdateTaskStatusRequest
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.shared.dto.task.UploadTaskPhotoRequest
import worklogtracker.shared.dto.task.RecordTaskLocationRequest

class TaskRepository(private val api: ApiClient) {
    private val baseUrl = "http://10.0.2.2:8080/api/tasks"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getTasks(): List<TaskResponse> {
        val response = api.get(baseUrl)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun updateTaskStatus(id: String, request: UpdateTaskStatusRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/$id/status", body)
    }

    suspend fun uploadPhoto(request: UploadTaskPhotoRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/photos", body)
    }

    suspend fun recordLocation(request: RecordTaskLocationRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/locations", body)
    }
}
