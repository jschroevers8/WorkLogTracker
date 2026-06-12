package worklogtracker.backend.presentation.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.task.AssignTaskUseCase
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.AssignTaskRequest

fun Route.assignTaskRoute(assignTaskUseCase: AssignTaskUseCase) {
    authenticate {
        post("/api/tasks/{id}/assign") {
            val userId = call.getUserId()
            val taskId = TaskId(call.parameters["id"]?.toInt() ?: error("Task ID required"))
            val request = call.receive<AssignTaskRequest>()

            call.respond(HttpStatusCode.OK, assignTaskUseCase(userId, taskId, UserId(request.assignedUserId)))
        }
    }
}
