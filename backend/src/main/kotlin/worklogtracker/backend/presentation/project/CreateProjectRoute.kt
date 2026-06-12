package worklogtracker.backend.presentation.project

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.project.CreateProjectUseCase
import worklogtracker.backend.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.project.CreateProjectRequest
import io.ktor.server.response.respond
import java.time.LocalDate

fun Route.createProjectRoute(createProjectUseCase: CreateProjectUseCase) {
    authenticate {
        post("/api/projects") {
            val userId = call.getUserId()
            val request = call.receive<CreateProjectRequest>()
            val response = createProjectUseCase(userId, request.name, request.description, request.startDate?.let(LocalDate::parse), request.endDate?.let(LocalDate::parse))

            call.respond(HttpStatusCode.Created, response)
        }
    }
}
