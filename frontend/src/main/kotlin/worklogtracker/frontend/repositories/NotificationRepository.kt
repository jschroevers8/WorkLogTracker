package worklogtracker.frontend.repositories

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.notification.NotificationResponse

class NotificationRepository(private val api: ApiClient) {
    private val baseUrl = "http://10.0.2.2:8080/api/notifications"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getNotifications(): List<NotificationResponse> {
        val response = api.get(baseUrl)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun markAsRead(id: Int): HttpResponse {
        return api.postWithoutBody("$baseUrl/$id/read")
    }
}
