package worklogtracker.backend.application.usecases.project

import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.backend.application.exceptions.ProjectCreationFailedException
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.factories.ProjectFactory
import worklogtracker.backend.domain.repositories.ProjectRepositoryInterface
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDate

class CreateProjectUseCase(
    private val projectRepository: ProjectRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val projectFactory: ProjectFactory
) {
    
    suspend operator fun invoke(
        userId: UserId,
        name: String,
        description: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): ProjectResponse {
        return try {
            val user = userRepository.findById(userId) ?: throw Exception("User not found")
            user.ensureIsAdmin()
            
            val project = projectFactory.create(
                name = name,
                description = description,
                startDate = startDate,
                endDate = endDate,
                createdById = userId
            )
            
            projectRepository.save(project).toResponse()
        } catch (e: UnauthorizedException) {
            throw e
        } catch (e: Exception) {
            print(e)

            throw ProjectCreationFailedException(e.message ?: "Unknown error")
        }
    }
}

