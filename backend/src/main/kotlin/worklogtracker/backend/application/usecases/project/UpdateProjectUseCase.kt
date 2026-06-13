package worklogtracker.backend.application.usecases.project

import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.backend.application.exceptions.ProjectUpdateFailedException
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.exceptions.ProjectNotFoundException
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.ProjectRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId

class UpdateProjectUseCase(
    private val projectRepository: ProjectRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val taskRepository: TaskRepositoryInterface
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
            if (status != null) {
                if (status == ProjectStatus.COMPLETED) {
                    val tasks = taskRepository.findByProject(projectId)
                    if (tasks.any { it.status != TaskStatus.COMPLETED }) {
                        throw Exception("Kan project niet afsluiten: er zijn nog onvoltooide taken")
                    }
                }
                project = project.updateStatus(status)
            }
            
            projectRepository.update(project)

            project.toResponse()
        } catch (e: UnauthorizedException) {
            throw e
        } catch (e: Exception) {
            throw ProjectUpdateFailedException(e.message ?: "Unknown error")
        }
    }
}

