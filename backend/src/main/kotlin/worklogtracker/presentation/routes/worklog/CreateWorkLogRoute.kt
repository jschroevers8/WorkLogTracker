package worklogtracker.presentation.routes.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.worklog.CreateManualWorkLogUseCase
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.presentation.dto.worklog.CreateWorkLogRequest
import java.time.LocalDateTime

fun Route.createWorkLogRoute(createManualWorkLogUseCase: CreateManualWorkLogUseCase) {
    authenticate {
        post("/api/v1/worklogs") {
            val userId = call.getUserId()
            val request = call.receive<CreateWorkLogRequest>()

            call.respond(HttpStatusCode.Created, createManualWorkLogUseCase(userId,
                TaskId(request.taskId), LocalDateTime.parse(request.startTime), LocalDateTime.parse(request.endTime), request.notes))
        }
    }
}
