package worklogtracker.backend.application.mappers

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.backend.domain.entities.TaskEntity
import java.time.format.DateTimeFormatter

fun TaskEntity.toResponse() =
    TaskResponse(
        id = id?.value,
        projectId = projectId.value,
        title = title,
        description = description,
        status = status.name,
        createdBy = createdBy.value,
        createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        updatedAt = updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )
