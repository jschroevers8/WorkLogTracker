package worklogtracker.backend.application.mappers

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.shared.dto.task.TaskLocationDto
import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.TaskLocationEntity
import worklogtracker.backend.domain.entities.TaskPhotoEntity
import java.time.format.DateTimeFormatter

fun TaskEntity.toResponse(
    photos: List<TaskPhotoEntity> = emptyList(),
    locations: List<TaskLocationEntity> = emptyList(),
    assignmentId: Int? = null,
    totalHours: Double = 0.0
) =
    TaskResponse(
        id = id?.value,
        projectId = projectId.value,
        title = title,
        description = description,
        status = status.name,
        createdBy = createdBy.value,
        createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        updatedAt = updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        assignmentId = assignmentId,
        photoUrls = photos.map { it.photoUrl },
        locations = locations.map { 
            TaskLocationDto(
                latitude = it.latitude,
                longitude = it.longitude,
                recordedAt = it.recordedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
        },
        totalHours = totalHours
    )
