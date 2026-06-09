package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.ProjectEntity
import worklogtracker.domain.entities.enums.ProjectStatus
import worklogtracker.domain.repositories.ProjectRepositoryInterface
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.hydrators.hydrateProject
import worklogtracker.infrastructure.tables.ProjectTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

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
            val now = Instant.now().toEpochMilli()

            ProjectTable.insert {
                it[name] = project.name
                it[description] = project.description
                it[status] = project.status.name
                it[startDate] = project.startDate?.let { date ->
                    Instant.from(
                        date.atStartOfDay(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[endDate] = project.endDate?.let { date ->
                    Instant.from(
                        date.atStartOfDay(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[createdById] = project.createdById.value
                it[createdAt] = now
                it[updatedAt] = now
            }

            project
        }

    override suspend fun update(project: ProjectEntity): Boolean =
        transaction {
            val now = Instant.now().toEpochMilli()

            ProjectTable.update({ ProjectTable.id eq project.id!!.value }) {
                it[name] = project.name
                it[description] = project.description
                it[status] = project.status.name
                it[endDate] = project.endDate?.let { date ->
                    Instant.from(
                        date.atStartOfDay(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
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

    override suspend fun findByUser(userId: UserId): List<ProjectEntity> =
        transaction {
            ProjectTable
                .selectAll()
                .where { ProjectTable.createdById eq userId.value }
                .map { it.hydrateProject() }
        }
}