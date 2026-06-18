package worklogtracker.backend.domain.entities

import org.junit.Test
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.exceptions.InvalidTaskStatusTransitionException
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaskEntityTest {
    private val now = LocalDateTime.now()
    private val projectId = ProjectId(1)
    private val userId = UserId(1)

    private fun createTask(status: TaskStatus) =
        TaskEntity(
            id = TaskId(1),
            projectId = projectId,
            title = "Test Task",
            description = "Description",
            status = status,
            createdBy = userId,
            createdAt = now,
            updatedAt = now,
        )

    @Test
    fun `should transition from OPEN to IN_PROGRESS`() {
        val task = createTask(TaskStatus.OPEN)
        val updatedTask = task.updateStatus(TaskStatus.IN_PROGRESS)
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.status)
    }

    @Test
    fun `should transition from IN_PROGRESS to COMPLETED`() {
        val task = createTask(TaskStatus.IN_PROGRESS)
        val updatedTask = task.updateStatus(TaskStatus.COMPLETED)
        assertEquals(TaskStatus.COMPLETED, updatedTask.status)
    }

    @Test
    fun `should throw exception for invalid transition from OPEN to COMPLETED`() {
        val task = createTask(TaskStatus.OPEN)
        assertFailsWith<InvalidTaskStatusTransitionException> {
            task.updateStatus(TaskStatus.COMPLETED)
        }
    }

    @Test
    fun `should complete task`() {
        val task = createTask(TaskStatus.IN_PROGRESS)
        val completedTask = task.complete()
        assertEquals(TaskStatus.COMPLETED, completedTask.status)
    }

    @Test
    fun `should cancel task from OPEN`() {
        val task = createTask(TaskStatus.OPEN)
        val cancelledTask = task.cancel()
        assertEquals(TaskStatus.CANCELLED, cancelledTask.status)
    }
}
