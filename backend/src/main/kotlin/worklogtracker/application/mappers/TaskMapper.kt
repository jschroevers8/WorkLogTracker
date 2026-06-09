package worklogtracker.application.mappers

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.domain.entities.TaskEntity
import java.time.format.DateTimeFormatter

fun TaskEntity.toResponse() =
    TaskResponse(
        id = id?.value,
        projectId = projectId.value,
        assignedUserId = assignedUserId?.value,
        title = title,
        description = description,
        estimatedHours = estimatedHours.toDouble(),
        actualHours = actualHours.toDouble(),
        deadline = deadline?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        priority = priority.name,
        status = status.name,
        createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        updatedAt = updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )
