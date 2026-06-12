package worklogtracker.frontend.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.auth.LoginRequest
import worklogtracker.shared.dto.auth.RegisterUserRequest
import worklogtracker.shared.dto.auth.AuthResponse

class UserRepository(private val api: ApiClient) {

    private val baseUrl = "http://10.0.2.2:8080/api"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun login(request: LoginRequest): AuthResponse {
        val requestBody = json.encodeToString(request)
        val response = api.post("$baseUrl/auth/login", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun register(request: RegisterUserRequest) {
        val requestBody = json.encodeToString(request)
        api.post("$baseUrl/auth/register", requestBody)
    }
}
