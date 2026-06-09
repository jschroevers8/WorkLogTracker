package worklogtracker.presentation.routes.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.worklog.GetUserWorkLogsUseCase
import worklogtracker.infrastructure.plugins.getUserId
import java.time.LocalDateTime

fun Route.getWorkLogsRoute(getUserWorkLogsUseCase: GetUserWorkLogsUseCase) {
    authenticate {
        get("/api/v1/worklogs") {
            val userId = call.getUserId()

            val from = call.parameters["from"]?.let(LocalDateTime::parse)
            val to = call.parameters["to"]?.let(LocalDateTime::parse)

            call.respond(HttpStatusCode.OK, getUserWorkLogsUseCase(userId, from, to))
        }
    }
}
