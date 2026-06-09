package worklogtracker.presentation.routes.report

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.repositories.WorkLogRepositoryInterface
import java.time.LocalDateTime
import worklogtracker.presentation.dto.report.UserReportResponse
import io.ktor.server.response.respond

//TODO
fun Route.getUserReportRoute(workLogRepository: WorkLogRepositoryInterface) {
    authenticate {
        get("/api/v1/reports/user/{userId}") {
            val requestedUserId = UserId(call.parameters["userId"]?.toInt() ?: error("User ID required"))
            val from = call.parameters["from"]?.let(LocalDateTime::parse)
            val to = call.parameters["to"]?.let(LocalDateTime::parse)
            val workLogs = workLogRepository.findByUser(requestedUserId, from, to)
            val totalMinutes = workLogs.sumOf { it.durationMinutes ?: 0 }
            val totalHours = totalMinutes / 60.0
            val taskCount = workLogs.map { it.taskId }.distinct().size
            call.respond(HttpStatusCode.OK,
                UserReportResponse(
                    requestedUserId.value,
                    "User ${requestedUserId.value}",
                    totalHours,
                    taskCount,
                    0,
                    if (taskCount > 0) totalHours / taskCount else 0.0,
                    "${from?.toLocalDate()} to ${to?.toLocalDate()}"
                )
            )
        }
    }
}
