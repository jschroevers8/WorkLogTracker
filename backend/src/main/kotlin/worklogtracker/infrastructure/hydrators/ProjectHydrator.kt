package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.ProjectEntity
import worklogtracker.domain.entities.enums.ProjectStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.tables.ProjectTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateProject() =
    ProjectEntity(
        id = ProjectId(this[ProjectTable.id]),
        name = this[ProjectTable.name],
        description = this[ProjectTable.description],
        status = ProjectStatus.valueOf(this[ProjectTable.status]),
        startDate = this[ProjectTable.startDate]?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        },
        endDate = this[ProjectTable.endDate]?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        },
        createdById = UserId(this[ProjectTable.createdById]),
        createdAt = Instant.ofEpochMilli(this[ProjectTable.createdAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        updatedAt = Instant.ofEpochMilli(this[ProjectTable.updatedAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    )