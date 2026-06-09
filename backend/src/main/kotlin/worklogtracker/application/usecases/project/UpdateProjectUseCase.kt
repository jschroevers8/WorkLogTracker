package worklogtracker.application.usecases.project

import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.application.exceptions.ProjectUpdateFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.entities.enums.ProjectStatus
import worklogtracker.domain.exceptions.ProjectNotFoundException
import worklogtracker.domain.exceptions.UnauthorizedException
import worklogtracker.domain.repositories.ProjectRepositoryInterface
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId

class UpdateProjectUseCase(
    private val projectRepository: ProjectRepositoryInterface,
    private val userRepository: UserRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId,
        projectId: ProjectId,
        name: String?,
        description: String?,
        status: ProjectStatus?
    ): ProjectResponse {
        return try {
            val user = userRepository.findById(userId) ?: throw Exception("User not found")
            user.ensureIsAdmin()
            
            var project = projectRepository.findById(projectId)
                ?: throw ProjectNotFoundException(projectId.value.toString())
            
            if (name != null) project = project.copy(name = name)
            if (description != null) project = project.copy(description = description)
            if (status != null) project = project.updateStatus(status)
            
            projectRepository.update(project)

            project.toResponse()
        } catch (e: UnauthorizedException) {
            throw e
        } catch (e: Exception) {
            throw ProjectUpdateFailedException(e.message ?: "Unknown error")
        }
    }
}

