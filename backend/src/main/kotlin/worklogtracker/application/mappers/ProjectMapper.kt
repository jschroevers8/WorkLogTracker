package worklogtracker.application.mappers

import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.domain.entities.ProjectEntity
import java.time.format.DateTimeFormatter

fun ProjectEntity.toResponse() =
    ProjectResponse(
        id = id?.value,
        name = name,
        description = description,
        status = status.name,
        startDate = startDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        endDate = endDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )