package worklogtracker.frontend.repositories

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.project.ProjectResponse
import kotlin.test.assertEquals
import org.junit.Test

class ProjectRepositoryTest {

    private val api = mockk<ApiClient>()
    private val repository = ProjectRepository(api)
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `getProjects should return list of projects`() = runTest {
        // Arrange
        val projects = listOf(
            ProjectResponse(1, "Project 1", "Description 1", "PLANNING", null, null, 1),
            ProjectResponse(2, "Project 2", "Description 2", "ACTIVE", null, null, 1)
        )
        val response = mockk<HttpResponse>()
        coEvery { response.bodyAsText() } returns json.encodeToString(projects)
        coEvery { api.get(any()) } returns response

        // Act
        val result = repository.getProjects()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Project 1", result[0].name)
        assertEquals("ACTIVE", result[1].status)
    }
}
