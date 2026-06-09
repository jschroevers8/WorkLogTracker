package worklogtracker.application.mappers

import worklogtracker.application.dto.worklog.WorkLogResponse
import worklogtracker.domain.entities.WorkLogEntity
import java.time.format.DateTimeFormatter


fun WorkLogEntity.toResponse() =
    WorkLogResponse(
        id = id?.value,
        taskId = taskId.value,
        userId = userId.value,
        startTime = startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        endTime = endTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        durationMinutes = durationMinutes,
        notes = notes,
        isSynced = isSynced,
        createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        updatedAt = updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    )
