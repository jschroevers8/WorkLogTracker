package worklogtracker.backend.presentation.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.worklog.GetUserWorkLogsUseCase
import worklogtracker.backend.infrastructure.plugins.getUserId
import java.time.LocalDateTime

fun Route.getWorkLogsRoute(getUserWorkLogsUseCase: GetUserWorkLogsUseCase) {
    authenticate {
        get("/api/worklogs") {
            val userId = call.getUserId()

            call.respond(HttpStatusCode.OK, getUserWorkLogsUseCase(userId))
        }
    }
}
