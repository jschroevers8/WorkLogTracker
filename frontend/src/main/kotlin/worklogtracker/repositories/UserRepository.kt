package worklogtracker.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.user.CreateAddressRequest
import worklogtracker.data.remote.user.CreateUser
import worklogtracker.data.remote.user.LoginRequest
import worklogtracker.data.remote.user.LoginResponse
import worklogtracker.data.remote.user.UserResponse

class UserRepository(private val api: ApiClient) {

    private val baseUrl = "http://10.0.2.2:8080"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        val requestBody = json.encodeToString(LoginRequest(email, password))
        val response = api.post("$baseUrl/login", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun signupUser(user: CreateUser): UserResponse {
        val requestBody = json.encodeToString(user)
        val response = api.post("$baseUrl/signup", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun requestRenter() {
        api.postWithoutBody("$baseUrl/user/request-renter")
    }

    suspend fun getUser(): UserResponse {
        val response = api.get("$baseUrl/user")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun createAddress(address: CreateAddressRequest) {
        val body = json.encodeToString(address)
        api.post("$baseUrl/user/address/create", body)
    }

    suspend fun getRenterRequests(): List<UserResponse> {
        val response = api.get("$baseUrl/admin/renter-requests")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun acceptRenterRequest(id: Int) {
        api.postWithoutBody("$baseUrl/admin/renter-requests/$id/approve")
    }
}