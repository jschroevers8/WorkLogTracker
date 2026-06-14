package worklogtracker.backend.infrastructure.hydrators

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.sql.ResultRow
import worklogtracker.backend.domain.entities.ProjectEntity
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.ProjectTable

fun ResultRow.hydrateProject() =
    ProjectEntity(
        id = ProjectId(this[ProjectTable.id]),
        name = this[ProjectTable.name],
        description = this[ProjectTable.description],
        status = ProjectStatus.valueOf(this[ProjectTable.status]),
        startDate = this[ProjectTable.startDate]?.toJavaLocalDate(),
        endDate = this[ProjectTable.endDate]?.toJavaLocalDate(),
        createdById = UserId(this[ProjectTable.createdById]),
        createdAt = this[ProjectTable.createdAt].toJavaLocalDateTime(),
        updatedAt = this[ProjectTable.updatedAt].toJavaLocalDateTime(),
    )
