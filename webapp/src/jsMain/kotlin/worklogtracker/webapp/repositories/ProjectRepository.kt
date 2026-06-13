package worklogtracker.webapp.repositories

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.shared.dto.project.CreateProjectRequest
import worklogtracker.shared.dto.project.UpdateProjectRequest

class ProjectRepository(private val client: HttpClient, private val baseUrl: String, private val getToken: () -> String?) {
    suspend fun getProjects(status: String? = null, excludeStatus: String? = null): List<ProjectResponse> {
        return client.get("$baseUrl/projects") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            if (status != null) parameter("status", status)
            if (excludeStatus != null) parameter("excludeStatus", excludeStatus)
        }.body()
    }

    suspend fun createProject(request: CreateProjectRequest): ProjectResponse {
        return client.post("$baseUrl/projects") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun updateProject(id: Int, request: UpdateProjectRequest): ProjectResponse {
        return client.put("$baseUrl/projects/$id") {
            header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
