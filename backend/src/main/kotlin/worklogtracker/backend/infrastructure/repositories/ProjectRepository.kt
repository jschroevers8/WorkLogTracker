package worklogtracker.backend.infrastructure.repositories

import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import worklogtracker.backend.domain.entities.ProjectEntity
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.repositories.ProjectRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.hydrators.hydrateProject
import worklogtracker.backend.infrastructure.tables.ProjectTable
import worklogtracker.backend.infrastructure.tables.TaskAssignmentTable
import worklogtracker.backend.infrastructure.tables.TaskTable
import java.time.LocalDateTime

class ProjectRepository : ProjectRepositoryInterface {
    override suspend fun findById(id: ProjectId): ProjectEntity? =
        transaction {
            ProjectTable
                .selectAll()
                .where { ProjectTable.id eq id.value }
                .map { it.hydrateProject() }
                .singleOrNull()
        }

    override suspend fun save(project: ProjectEntity): ProjectEntity =
        transaction {
            val now = LocalDateTime.now().toKotlinLocalDateTime()

            ProjectTable.insert {
                it[name] = project.name
                it[description] = project.description
                it[status] = project.status.name
                it[startDate] = project.startDate?.toKotlinLocalDate()
                it[endDate] = project.endDate?.toKotlinLocalDate()
                it[createdById] = project.createdById.value
                it[createdAt] = now
                it[updatedAt] = now
            }

            project
        }

    override suspend fun update(project: ProjectEntity): Boolean =
        transaction {
            val now = LocalDateTime.now().toKotlinLocalDateTime()

            ProjectTable.update({ ProjectTable.id eq project.id!!.value }) {
                it[name] = project.name
                it[description] = project.description
                it[status] = project.status.name
                it[endDate] = project.endDate?.toKotlinLocalDate()
                it[updatedAt] = now
            } > 0
        }

    override suspend fun delete(id: ProjectId): Boolean =
        transaction {
            ProjectTable.deleteWhere { ProjectTable.id eq id.value } > 0
        }

    override suspend fun findAll(status: ProjectStatus?): List<ProjectEntity> =
        transaction {
            if (status != null) {
                ProjectTable
                    .selectAll()
                    .where { ProjectTable.status eq status.name }
                    .map { it.hydrateProject() }
            } else {
                ProjectTable
                    .selectAll()
                    .map { it.hydrateProject() }
            }
        }

    override suspend fun findAllExcludingStatus(status: ProjectStatus): List<ProjectEntity> =
        transaction {
            ProjectTable
                .selectAll()
                .where { ProjectTable.status neq status.name }
                .map { it.hydrateProject() }
        }

    override suspend fun findByUser(userId: UserId): List<ProjectEntity> =
        transaction {
            ProjectTable
                .selectAll()
                .where { ProjectTable.createdById eq userId.value }
                .map { it.hydrateProject() }
        }

    override suspend fun findByInvolvedUser(userId: UserId): List<ProjectEntity> =
        transaction {
            val projectsFromAssignments =
                (ProjectTable innerJoin TaskTable innerJoin TaskAssignmentTable)
                    .select(ProjectTable.columns)
                    .where { TaskAssignmentTable.userId eq userId.value }
                    .map { it.hydrateProject() }

            val projectsCreatedByUser =
                ProjectTable
                    .selectAll()
                    .where { ProjectTable.createdById eq userId.value }
                    .map { it.hydrateProject() }

            (projectsFromAssignments + projectsCreatedByUser).distinctBy { it.id }
        }
}
