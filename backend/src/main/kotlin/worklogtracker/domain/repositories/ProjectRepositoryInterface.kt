package worklogtracker.domain.repositories

import worklogtracker.domain.entities.ProjectEntity
import worklogtracker.domain.entities.enums.ProjectStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId

interface ProjectRepositoryInterface {
    suspend fun findById(id: ProjectId): ProjectEntity?
    suspend fun save(project: ProjectEntity): ProjectEntity
    suspend fun update(project: ProjectEntity): Boolean
    suspend fun delete(id: ProjectId): Boolean
    suspend fun findAll(status: ProjectStatus? = null): List<ProjectEntity>
    suspend fun findByUser(userId: UserId): List<ProjectEntity>
}

