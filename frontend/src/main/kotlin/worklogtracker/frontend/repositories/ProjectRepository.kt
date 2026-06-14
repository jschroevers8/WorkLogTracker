package worklogtracker.frontend.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.project.ProjectResponse

class ProjectRepository(
    private val api: ApiClient,
) {
    private val baseUrl = "http://10.0.2.2:8080/api/projects"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getProjects(): List<ProjectResponse> {
        val response = api.get(baseUrl)
        return json.decodeFromString(response.bodyAsText())
    }
}
