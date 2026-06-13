package worklogtracker.backend.presentation.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.worklog.GetUserWorkLogsUseCase
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.infrastructure.plugins.getUserId

fun Route.getWorkLogsRoute(getUserWorkLogsUseCase: GetUserWorkLogsUseCase) {
    authenticate {
        get("/api/worklogs") {
            val userId = call.getUserId()
            val taskAssignmentId = call.parameters["taskAssignmentId"]?.toInt()?.let(::TaskAssignmentId)

            call.respond(HttpStatusCode.OK, getUserWorkLogsUseCase(userId, taskAssignmentId))
        }
    }
}
