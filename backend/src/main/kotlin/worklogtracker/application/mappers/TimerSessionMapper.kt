package worklogtracker.application.mappers

import worklogtracker.application.dto.worklog.TimerSessionResponse
import worklogtracker.domain.entities.TimerSessionEntity
import java.time.format.DateTimeFormatter

fun TimerSessionEntity.toResponse() =
    TimerSessionResponse(
        id = id!!.value,
        taskId = taskId.value,
        userId = userId.value,
        startedAt = startedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        endedAt = endedAt?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        isRunning = isRunning
    )
