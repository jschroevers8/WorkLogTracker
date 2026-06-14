package worklogtracker.backend.presentation.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.task.CreateTaskUseCase
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.CreateTaskRequest

fun Route.createTaskRoute(createTaskUseCase: CreateTaskUseCase) {
    authenticate {
        post("/api/tasks") {
            val userId = call.getUserId()
            val request = call.receive<CreateTaskRequest>()
            val response =
                createTaskUseCase(
                    userId = userId,
                    projectId = ProjectId(request.projectId),
                    title = request.title,
                    description = request.description,
                    assignedUserId = UserId(request.assignedUserId),
                )

            call.respond(HttpStatusCode.Created, response)
        }
    }
}
