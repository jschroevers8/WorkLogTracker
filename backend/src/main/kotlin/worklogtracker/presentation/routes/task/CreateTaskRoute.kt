package worklogtracker.presentation.routes.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.task.CreateTaskUseCase
import worklogtracker.domain.entities.enums.Priority
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.CreateTaskRequest
import java.math.BigDecimal
import java.time.LocalDateTime

fun Route.createTaskRoute(createTaskUseCase: CreateTaskUseCase) {
    authenticate {
        post("/api/v1/tasks") {
            val userId = call.getUserId()
            val request = call.receive<CreateTaskRequest>()
            val response = createTaskUseCase(userId,
                ProjectId(request.projectId), request.title, request.description, BigDecimal(request.estimatedHours), request.deadline?.let(LocalDateTime::parse), Priority.valueOf(request.priority))

            call.respond(HttpStatusCode.Created, response)
        }
    }
}
