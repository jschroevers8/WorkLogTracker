package worklogtracker.presentation.project

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.project.UpdateProjectUseCase
import worklogtracker.domain.entities.enums.ProjectStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.project.UpdateProjectRequest

fun Route.updateProjectRoute(updateProjectUseCase: UpdateProjectUseCase) {
    authenticate {
        put("/api/projects/{id}") {
            val userId = call.getUserId()
            val projectId = ProjectId(call.parameters["id"]?.toInt() ?: error("Project ID required"))
            val request = call.receive<UpdateProjectRequest>()
            val response = updateProjectUseCase(userId, projectId, request.name, request.description, request.status?.let(
                ProjectStatus::valueOf))

            call.respond(HttpStatusCode.OK, response)
        }
    }
}
