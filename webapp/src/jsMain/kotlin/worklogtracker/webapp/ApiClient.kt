package worklogtracker.webapp

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.auth.LoginRequest
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.shared.dto.project.CreateProjectRequest
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import worklogtracker.shared.dto.worklog.WorkLogResponse
import io.ktor.client.statement.*
import worklogtracker.shared.dto.common.ErrorResponse

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    private val baseUrl = "http://localhost:8080/api"
    private var token: String? = null

    fun setToken(newToken: String?) {
        token = newToken
    }

    suspend fun login(request: LoginRequest): AuthResponse {
        val response = client.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        if (response.status.isSuccess()) {
            return response.body()
        } else {
            val errorBody = try {
                response.body<ErrorResponse>()
            } catch (e: Exception) {
                null
            }
            throw Exception(errorBody?.message ?: "Inloggen mislukt (Status: ${response.status.value})")
        }
    }

    suspend fun getUsers(): List<UserResponse> {
        return client.get("$baseUrl/users") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun getUserWorkLogs(userId: Int): List<WorkLogResponse> {
        return client.get("$baseUrl/worklogs") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("userId", userId)
        }.body()
    }

    suspend fun getProjects(): List<ProjectResponse> {
        return client.get("$baseUrl/projects") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun createProject(request: CreateProjectRequest): ProjectResponse {
        return client.post("$baseUrl/projects") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getTasks(): List<TaskResponse> {
        return client.get("$baseUrl/tasks") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun createTask(request: CreateTaskRequest): TaskResponse {
        return client.post("$baseUrl/tasks") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun createWorkLog(request: CreateWorkLogRequest): WorkLogResponse {
        return client.post("$baseUrl/worklogs") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
