package worklogtracker.webapp.repositories

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import worklogtracker.shared.dto.user.UserResponse

class UserRepository(private val client: HttpClient, private val baseUrl: String, private val getToken: () -> String?) {
    suspend fun getUsers(): List<UserResponse> {
        return client.get("$baseUrl/users") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
        }.body()
    }
}
