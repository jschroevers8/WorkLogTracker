package worklogtracker.backend.domain.entities

import org.junit.Test
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProjectEntityTest {

    private val now = LocalDateTime.now()
    private val userId = UserId(1)

    private fun createProject(
        status: ProjectStatus = ProjectStatus.PLANNING,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ) = ProjectEntity(
        id = ProjectId(1),
        name = "Test Project",
        description = "Description",
        status = status,
        startDate = startDate,
        endDate = endDate,
        createdById = userId,
        createdAt = now,
        updatedAt = now
    )

    @Test
    fun `should fail when startDate is after endDate`() {
        assertFailsWith<IllegalArgumentException> {
            createProject(
                startDate = LocalDate.now().plusDays(1),
                endDate = LocalDate.now()
            )
        }
    }

    @Test
    fun `should succeed when startDate is before endDate`() {
        val project = createProject(
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1)
        )
        assertNotNull(project)
    }

    @Test
    fun `updateStatus should transition from PLANNING to ACTIVE`() {
        val project = createProject(ProjectStatus.PLANNING)
        val updated = project.updateStatus(ProjectStatus.ACTIVE)
        assertEquals(ProjectStatus.ACTIVE, updated.status)
    }

    @Test
    fun `updateStatus should fail for invalid transition from PLANNING to COMPLETED`() {
        val project = createProject(ProjectStatus.PLANNING)
        assertFailsWith<IllegalArgumentException> {
            project.updateStatus(ProjectStatus.COMPLETED)
        }
    }

    @Test
    fun `isActive should return true only for ACTIVE status`() {
        assertTrue(createProject(ProjectStatus.ACTIVE).isActive())
        assertFalse(createProject(ProjectStatus.PLANNING).isActive())
        assertFalse(createProject(ProjectStatus.COMPLETED).isActive())
    }

    private fun assertNotNull(actual: Any?) {
        assertTrue(actual != null)
    }
}
