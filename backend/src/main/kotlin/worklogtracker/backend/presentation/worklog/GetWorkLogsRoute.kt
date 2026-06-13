package worklogtracker.backend.presentation.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.worklog.GetUserWorkLogsUseCase
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId

fun Route.getWorkLogsRoute(getUserWorkLogsUseCase: GetUserWorkLogsUseCase) {
    authenticate {
        get("/api/worklogs") {
            val taskAssignmentId = call.request.queryParameters["taskAssignmentId"]?.toInt()?.let(::TaskAssignmentId)
            val userId = call.request.queryParameters["userId"]?.toInt()?.let(::UserId)

            call.respond(HttpStatusCode.OK, getUserWorkLogsUseCase(userId!!, taskAssignmentId))
        }
    }
}
