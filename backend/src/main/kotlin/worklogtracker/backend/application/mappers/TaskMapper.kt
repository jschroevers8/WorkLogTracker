package worklogtracker.backend.application.mappers

import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.TaskLocationEntity
import worklogtracker.backend.domain.entities.TaskPhotoEntity
import worklogtracker.shared.dto.task.TaskLocation
import worklogtracker.shared.dto.task.TaskResponse
import java.time.format.DateTimeFormatter

fun TaskEntity.toResponse(
    photos: List<TaskPhotoEntity> = emptyList(),
    locations: List<TaskLocationEntity> = emptyList(),
    assignmentId: Int? = null,
    totalHours: Double = 0.0,
) = TaskResponse(
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
    locations =
        locations.map {
            TaskLocation(
                latitude = it.latitude,
                longitude = it.longitude,
                recordedAt = it.recordedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            )
        },
    totalHours = totalHours,
)
