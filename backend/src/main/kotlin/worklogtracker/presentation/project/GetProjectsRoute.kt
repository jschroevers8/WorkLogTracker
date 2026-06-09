package worklogtracker.presentation.project

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.project.ListProjectsUseCase
import worklogtracker.infrastructure.plugins.getUserId

fun Route.getProjectsRoute(listProjectsUseCase: ListProjectsUseCase) {
    authenticate {
        get("/api/projects") {
            call.respond(
                HttpStatusCode.OK,
                listProjectsUseCase(call.getUserId()),
                )
        }
    }
}
