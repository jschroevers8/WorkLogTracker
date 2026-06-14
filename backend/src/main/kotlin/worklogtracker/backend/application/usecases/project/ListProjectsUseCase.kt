package worklogtracker.backend.application.usecases.project

import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.entities.enums.UserRole
import worklogtracker.backend.domain.repositories.ProjectRepositoryInterface
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.shared.dto.project.ProjectResponse

class ListProjectsUseCase(
    private val projectRepository: ProjectRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
) {
    suspend operator fun invoke(
        userId: UserId,
        status: ProjectStatus? = null,
        excludeStatus: ProjectStatus? = null,
    ): List<ProjectResponse> {
        val user = userRepository.findById(userId) ?: throw Exception("User not found")

        // Admins can see everything based on filter
        // Others see projects they created OR projects they are involved in via tasks
        if (user.role != UserRole.ADMIN) {
            return projectRepository.findByInvolvedUser(userId).map { it.toResponse() }
        }

        return when {
            status != null -> projectRepository.findAll(status).map { it.toResponse() }
            excludeStatus != null -> projectRepository.findAllExcludingStatus(excludeStatus).map { it.toResponse() }
            else -> projectRepository.findAll().map { it.toResponse() }
        }
    }
}
