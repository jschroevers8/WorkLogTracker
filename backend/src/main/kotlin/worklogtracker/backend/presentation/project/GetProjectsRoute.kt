package worklogtracker.backend.presentation.project

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.project.ListProjectsUseCase
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.infrastructure.plugins.getUserId

fun Route.getProjectsRoute(listProjectsUseCase: ListProjectsUseCase) {
    authenticate {
        get("/api/projects") {
            val status = call.request.queryParameters["status"]?.let { ProjectStatus.valueOf(it) }
            val excludeStatus = call.request.queryParameters["excludeStatus"]?.let { ProjectStatus.valueOf(it) }

            call.respond(
                HttpStatusCode.OK,
                listProjectsUseCase(
                    userId = call.getUserId(),
                    status = status,
                    excludeStatus = excludeStatus
                )
            )
        }
    }
}
