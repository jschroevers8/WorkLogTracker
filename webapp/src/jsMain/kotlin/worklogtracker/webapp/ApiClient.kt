package worklogtracker.webapp

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import worklogtracker.webapp.repositories.AuthRepository
import worklogtracker.webapp.repositories.ProjectRepository
import worklogtracker.webapp.repositories.TaskRepository
import worklogtracker.webapp.repositories.UserRepository
import worklogtracker.webapp.repositories.WorkLogRepository

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

    val auth = AuthRepository(client, baseUrl)
    val users = UserRepository(client, baseUrl) { token }
    val projects = ProjectRepository(client, baseUrl) { token }
    val tasks = TaskRepository(client, baseUrl) { token }
    val worklogs = WorkLogRepository(client, baseUrl) { token }

    fun setToken(newToken: String?) {
        token = newToken
    }
}
