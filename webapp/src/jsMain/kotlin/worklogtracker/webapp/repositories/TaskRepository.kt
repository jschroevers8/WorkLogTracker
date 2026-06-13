package worklogtracker.webapp.repositories

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.task.UploadTaskPhotoRequest
import worklogtracker.shared.dto.task.RecordTaskLocationRequest
import worklogtracker.shared.dto.task.UpdateTaskStatusRequest

class TaskRepository(private val client: HttpClient, private val baseUrl: String, private val getToken: () -> String?) {
    suspend fun getTasks(projectId: Int? = null): List<TaskResponse> {
        return client.get("$baseUrl/tasks") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            if (projectId != null) {
                parameter("projectId", projectId)
            }
        }.body()
    }

    suspend fun createTask(request: CreateTaskRequest): TaskResponse {
        return client.post("$baseUrl/tasks") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun assignTask(taskId: Int, request: AssignTaskRequest): HttpResponse {
        return client.post("$baseUrl/tasks/$taskId/assign") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    suspend fun uploadPhoto(request: UploadTaskPhotoRequest): HttpResponse {
        return client.post("$baseUrl/tasks/photos") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    suspend fun recordLocation(request: RecordTaskLocationRequest): HttpResponse {
        return client.post("$baseUrl/tasks/locations") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    suspend fun updateTaskStatus(taskId: Int, request: UpdateTaskStatusRequest): HttpResponse {
        return client.put("$baseUrl/tasks/$taskId/status") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
