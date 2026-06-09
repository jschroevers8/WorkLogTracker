package worklogtracker.application.usecases.project

import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.entities.enums.UserRole
import worklogtracker.domain.repositories.ProjectRepositoryInterface
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId

class ListProjectsUseCase(
    private val projectRepository: ProjectRepositoryInterface,
    private val userRepository: UserRepositoryInterface
) {
    
    suspend operator fun invoke(userId: UserId): List<ProjectResponse> {
        val user = userRepository.findById(userId) ?: throw Exception("User not found")
        
        return when (user.role) {
            UserRole.ADMIN -> projectRepository.findAll().map { it.toResponse()}
            else -> projectRepository.findByUser(userId).map { it.toResponse() }
        }
    }
}

