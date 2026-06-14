package worklogtracker.webapp.repositories

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.auth.LoginRequest
import worklogtracker.shared.dto.common.ErrorResponse

class AuthRepository(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun login(request: LoginRequest): AuthResponse {
        val response =
            client.post("$baseUrl/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

        if (response.status.isSuccess()) {
            return response.body()
        }

        val errorBody =
            try {
                response.body<ErrorResponse>()
            } catch (e: Exception) {
                null
            }

        throw Exception(errorBody?.message ?: "Inloggen mislukt (Status: ${response.status.value})")
    }
}
