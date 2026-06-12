package worklogtracker.backend.presentation.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.worklog.LogTimeUseCase
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import java.math.BigDecimal

fun Route.logTimeRoute(logTimeUseCase: LogTimeUseCase) {
    authenticate {
        post("/api/time-entries") {
            val userId = call.getUserId()
            val request = call.receive<CreateWorkLogRequest>()

            call.respond(
                HttpStatusCode.Created, logTimeUseCase(
                    userId = userId,
                    taskAssignmentId = TaskAssignmentId(request.taskAssignmentId),
                    hours = BigDecimal.valueOf(request.hours),
                    description = request.description
                )
            )
        }
    }
}
