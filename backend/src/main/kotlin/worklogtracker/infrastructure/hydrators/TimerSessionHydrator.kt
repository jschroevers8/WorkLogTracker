package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.TimerSessionEntity
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.timer.TimerSessionId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.tables.TimerSessionTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateTimerSession() =
    TimerSessionEntity(
        id = TimerSessionId(this[TimerSessionTable.id]),
        taskId = TaskId(this[TimerSessionTable.taskId]),
        userId = UserId(this[TimerSessionTable.userId]),
        startedAt = Instant.ofEpochMilli(this[TimerSessionTable.startedAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        endedAt = this[TimerSessionTable.endedAt]?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        },
        isRunning = this[TimerSessionTable.isRunning],
        createdAt = Instant.ofEpochMilli(this[TimerSessionTable.createdAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        updatedAt = Instant.ofEpochMilli(this[TimerSessionTable.updatedAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    )