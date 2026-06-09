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
import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.shared.dto.project.ProjectResponse

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
        return client.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
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
}
