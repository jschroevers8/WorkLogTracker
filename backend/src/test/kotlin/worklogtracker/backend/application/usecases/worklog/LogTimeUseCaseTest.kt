package worklogtracker.backend.application.usecases.worklog

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import worklogtracker.backend.application.usecases.ai.GenerateAiDescriptionUseCase
import worklogtracker.backend.domain.entities.TaskAssignmentEntity
import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskAssignmentRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.repositories.TimeEntryRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LogTimeUseCaseTest {

    private val timeEntryRepository = mockk<TimeEntryRepositoryInterface>(relaxed = true)
    private val taskAssignmentRepository = mockk<TaskAssignmentRepositoryInterface>(relaxed = true)
    private val taskRepository = mockk<TaskRepositoryInterface>(relaxed = true)
    private val generateAiDescriptionUseCase = mockk<GenerateAiDescriptionUseCase>()
    private val useCase = LogTimeUseCase(
        timeEntryRepository,
        taskAssignmentRepository,
        taskRepository,
        generateAiDescriptionUseCase
    )

    private val userId = UserId(1)
    private val taskId = TaskId(1)
    private val assignmentId = TaskAssignmentId(1)
    private val projectId = ProjectId(1)
    private val now = LocalDateTime.now()

    @Test
    fun `should log time and complete task successfully`() {
        runBlocking {
            // Arrange
            val hours = BigDecimal("2.5")
            val description = "Werk aan feature X"
            val aiDescription = "Professionele samenvatting van werk aan feature X"

            val assignment = TaskAssignmentEntity(
                id = assignmentId,
                taskId = taskId,
                userId = userId,
                assignedAt = now,
                status = TaskStatus.IN_PROGRESS
            )

            val task = TaskEntity(
                id = taskId,
                projectId = projectId,
                title = "Taak 1",
                description = "Beschrijving",
                status = TaskStatus.IN_PROGRESS,
                createdBy = userId,
                createdAt = now,
                updatedAt = now
            )

            coEvery { taskAssignmentRepository.findById(assignmentId) } returns assignment
            coEvery { taskRepository.findById(taskId) } returns task
            coEvery { generateAiDescriptionUseCase.invoke("Taak 1", description) } returns aiDescription

            // Act
            val result = useCase.invoke(userId, assignmentId, hours, description)

            // Assert
            assertTrue(result)
            coVerify {
                timeEntryRepository.save(match {
                    it.userId == userId &&
                    it.taskAssignmentId == assignmentId &&
                    it.hours == hours &&
                    it.description == description &&
                    it.aiDescription == aiDescription
                })
            }
            coVerify {
                taskRepository.update(match {
                    it.id == taskId && it.status == TaskStatus.COMPLETED
                })
            }
            coVerify {
                taskAssignmentRepository.update(match {
                    it.id == assignmentId && it.status == TaskStatus.COMPLETED
                })
            }
        }
    }

    @Test
    fun `should throw exception when assignment not found`() {
        runBlocking {
            // Arrange
            coEvery { taskAssignmentRepository.findById(assignmentId) } returns null

            // Act & Assert
            val exception = try {
                useCase.invoke(userId, assignmentId, BigDecimal("1"), null)
                null
            } catch (e: Exception) {
                e
            }

            assertEquals("Task assignment not found", exception?.message)
        }
    }

    @Test
    fun `should throw exception when task not found`() {
        runBlocking {
            // Arrange
            val assignment = TaskAssignmentEntity(
                id = assignmentId,
                taskId = taskId,
                userId = userId,
                assignedAt = now,
                status = TaskStatus.IN_PROGRESS
            )
            coEvery { taskAssignmentRepository.findById(assignmentId) } returns assignment
            coEvery { taskRepository.findById(taskId) } returns null

            // Act & Assert
            val exception = try {
                useCase.invoke(userId, assignmentId, BigDecimal("1"), null)
                null
            } catch (e: Exception) {
                e
            }

            assertEquals("Task not found", exception?.message)
        }
    }
}
