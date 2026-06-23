package worklogtracker.backend.application.usecases.project

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import worklogtracker.backend.application.exceptions.ProjectCreationFailedException
import worklogtracker.backend.domain.entities.ProjectEntity
import worklogtracker.backend.domain.entities.UserEntity
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.entities.enums.UserRole
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.factories.ProjectFactory
import worklogtracker.backend.domain.repositories.ProjectRepositoryInterface
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.backend.domain.valueobjects.user.Password
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateProjectUseCaseTest {

    private val projectRepository = mockk<ProjectRepositoryInterface>()
    private val userRepository = mockk<UserRepositoryInterface>()
    private val projectFactory = mockk<ProjectFactory>()
    private val useCase = CreateProjectUseCase(projectRepository, userRepository, projectFactory)

    private val adminUserId = UserId(1)
    private val regularUserId = UserId(2)
    private val now = LocalDateTime.now()

    private val adminUser = UserEntity(
        id = adminUserId,
        email = Email("admin@example.com"),
        passwordHash = Password("hashed"),
        firstName = "Admin",
        lastName = "User",
        role = UserRole.ADMIN,
        createdAt = now,
        updatedAt = now
    )

    private val regularUser = UserEntity(
        id = regularUserId,
        email = Email("user@example.com"),
        passwordHash = Password("hashed"),
        firstName = "Regular",
        lastName = "User",
        role = UserRole.EMPLOYEE,
        createdAt = now,
        updatedAt = now
    )

    @Test
    fun `should create project successfully when user is admin`() {
        runBlocking {
            // Arrange
            val name = "New Project"
            val description = "Description"
            val startDate = LocalDate.now()
            val endDate = startDate.plusMonths(1)

            val project = ProjectEntity(
                id = ProjectId(1),
                name = name,
                description = description,
                status = ProjectStatus.PLANNING,
                startDate = startDate,
                endDate = endDate,
                createdById = adminUserId,
                createdAt = now,
                updatedAt = now
            )

            coEvery { userRepository.findById(adminUserId) } returns adminUser
            every {
                projectFactory.create(name, description, startDate, endDate, adminUserId)
            } returns project
            coEvery { projectRepository.save(project) } returns project

            // Act
            val result = useCase.invoke(adminUserId, name, description, startDate, endDate)

            // Assert
            assertEquals(name, result.name)
            assertEquals(description, result.description)
            assertEquals(ProjectStatus.PLANNING.name, result.status)
        }
    }

    @Test
    fun `should throw UnauthorizedException when user is not admin`() {
        runBlocking {
            // Arrange
            coEvery { userRepository.findById(regularUserId) } returns regularUser

            // Act & Assert
            assertFailsWith<UnauthorizedException> {
                useCase.invoke(regularUserId, "Project", "Desc", null, null)
            }
        }
    }

    @Test
    fun `should throw ProjectCreationFailedException when user not found`() {
        runBlocking {
            // Arrange
            coEvery { userRepository.findById(adminUserId) } returns null

            // Act & Assert
            val exception = assertFailsWith<ProjectCreationFailedException> {
                useCase.invoke(adminUserId, "Project", "Desc", null, null)
            }
            assertEquals("User not found", exception.message)
        }
    }

    @Test
    fun `should throw ProjectCreationFailedException when repository fails`() {
        runBlocking {
            // Arrange
            val name = "Project"
            val project = ProjectEntity(
                name = name,
                description = null,
                status = ProjectStatus.PLANNING,
                startDate = null,
                endDate = null,
                createdById = adminUserId,
                createdAt = now,
                updatedAt = now
            )

            coEvery { userRepository.findById(adminUserId) } returns adminUser
            every {
                projectFactory.create(name, any(), any(), any(), adminUserId)
            } returns project
            coEvery { projectRepository.save(project) } throws Exception("DB Error")

            // Act & Assert
            val exception = assertFailsWith<ProjectCreationFailedException> {
                useCase.invoke(adminUserId, name, null, null, null)
            }
            assertEquals("DB Error", exception.message)
        }
    }
}
