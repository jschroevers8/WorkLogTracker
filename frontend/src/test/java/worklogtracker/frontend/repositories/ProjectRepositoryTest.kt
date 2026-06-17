package worklogtracker.frontend.repositories

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.shared.dto.project.ProjectResponse

class ProjectRepositoryTest {

    private val api = mockk<ApiClient>()
    private val repository = ProjectRepository(api)

    @Test
    fun `getProjects should return list of projects`() = runTest {
        val projects = listOf(
            ProjectResponse(
                1,
                "Project 1",
                "Description 1",
                "PLANNING",
                null,
                null,
                "2026-06-05"
            ),
            ProjectResponse(
                2,
                "Project 2",
                "Description 2",
                "ACTIVE",
                null,
                null,
                "2026-06-05"
            )
        )

        val json = Json.encodeToString(projects)

        val response = mockk<HttpResponse>()

        mockkStatic("io.ktor.client.statement.HttpResponseKt")

        coEvery { api.get(any()) } returns response
        coEvery { response.bodyAsText(any()) } returns json

        val result = repository.getProjects()

        assertEquals(2, result.size)
        assertEquals("Project 1", result[0].name)
        assertEquals("ACTIVE", result[1].status)
    }
}