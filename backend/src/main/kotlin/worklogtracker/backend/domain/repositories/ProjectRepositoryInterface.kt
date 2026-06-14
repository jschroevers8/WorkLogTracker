package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.ProjectEntity
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId

interface ProjectRepositoryInterface {
    suspend fun findById(id: ProjectId): ProjectEntity?
    suspend fun save(project: ProjectEntity): ProjectEntity
    suspend fun update(project: ProjectEntity): Boolean
    suspend fun delete(id: ProjectId): Boolean
    suspend fun findAll(status: ProjectStatus? = null): List<ProjectEntity>
    suspend fun findAllExcludingStatus(status: ProjectStatus): List<ProjectEntity>
    suspend fun findByUser(userId: UserId): List<ProjectEntity>
    suspend fun findByInvolvedUser(userId: UserId): List<ProjectEntity>
}

