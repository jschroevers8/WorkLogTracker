package worklogtracker.webapp.repositories

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.TaskResponse

class TaskRepository(
    private val client: HttpClient,
    private val baseUrl: String,
    private val getToken: () -> String?,
) {
    suspend fun getTasks(projectId: Int? = null): List<TaskResponse> =
        client
            .get("$baseUrl/tasks") {
                header(HttpHeaders.Authorization, "Bearer ${getToken()}")
                if (projectId != null) {
                    parameter("projectId", projectId)
                }
            }.body()

    suspend fun createTask(request: CreateTaskRequest): TaskResponse =
        client
            .post("$baseUrl/tasks") {
                header(HttpHeaders.Authorization, "Bearer ${getToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

    suspend fun assignTask(
        taskId: Int,
        request: AssignTaskRequest,
    ): HttpResponse =
        client.post("$baseUrl/tasks/$taskId/assign") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
}
