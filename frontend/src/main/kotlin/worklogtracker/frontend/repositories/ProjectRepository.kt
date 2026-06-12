package worklogtracker.frontend.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.project.CreateProjectRequest
import worklogtracker.shared.dto.project.UpdateProjectRequest
import worklogtracker.shared.dto.project.ProjectResponse

class ProjectRepository(private val api: ApiClient) {
    private val baseUrl = "http://10.0.2.2:8080/api/projects"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getProjects(): List<ProjectResponse> {
        val response = api.get(baseUrl)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun createProject(request: CreateProjectRequest) {
        val body = json.encodeToString(request)
        api.post(baseUrl, body)
    }

    suspend fun updateProject(id: String, request: UpdateProjectRequest) {
        val body = json.encodeToString(request)
        api.post("$baseUrl/$id", body) // In de backend is dit waarschijnlijk een PUT of PATCH, maar de ApiClient heeft alleen post
    }
}
