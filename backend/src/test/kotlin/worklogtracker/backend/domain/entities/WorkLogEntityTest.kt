package worklogtracker.backend.domain.entities

import org.junit.Test
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.domain.valueobjects.worklog.WorkLogId
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WorkLogEntityTest {

    private val now = LocalDateTime.now()
    private val userId = UserId(1)
    private val taskId = TaskId(1)

    @Test
    fun `should fail when endTime is before startTime`() {
        assertFailsWith<IllegalArgumentException> {
            WorkLogEntity(
                id = WorkLogId(1),
                taskId = taskId,
                userId = userId,
                startTime = now,
                endTime = now.minusHours(1),
                durationMinutes = 0,
                createdAt = now,
                updatedAt = now
            )
        }
    }

    @Test
    fun `should fail when endTime is in the future`() {
        assertFailsWith<IllegalArgumentException> {
            WorkLogEntity(
                id = WorkLogId(1),
                taskId = taskId,
                userId = userId,
                startTime = now.minusHours(2),
                endTime = now.plusMinutes(5),
                durationMinutes = 0,
                createdAt = now,
                updatedAt = now
            )
        }
    }

    @Test
    fun `isComplete should return true when endTime is set`() {
        val workLog = WorkLogEntity(
            taskId = taskId,
            userId = userId,
            startTime = now.minusHours(1),
            endTime = now,
            durationMinutes = 60,
            createdAt = now,
            updatedAt = now
        )
        assertTrue(workLog.isComplete())
    }

    @Test
    fun `isComplete should return false when endTime is null`() {
        val workLog = WorkLogEntity(
            taskId = taskId,
            userId = userId,
            startTime = now.minusHours(1),
            endTime = null,
            durationMinutes = null,
            createdAt = now,
            updatedAt = now
        )
        assertFalse(workLog.isComplete())
    }

    @Test
    fun `markAsSynced should update isSynced flag`() {
        val workLog = WorkLogEntity(
            taskId = taskId,
            userId = userId,
            startTime = now.minusHours(1),
            endTime = now,
            durationMinutes = 60,
            isSynced = false,
            createdAt = now,
            updatedAt = now
        )
        val synced = workLog.markAsSynced()
        assertTrue(synced.isSynced)
    }
}
