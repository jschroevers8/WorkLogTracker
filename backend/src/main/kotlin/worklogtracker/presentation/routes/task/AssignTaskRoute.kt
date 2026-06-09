package worklogtracker.presentation.routes.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.task.AssignTaskUseCase
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.AssignTaskRequest

fun Route.assignTaskRoute(assignTaskUseCase: AssignTaskUseCase) {
    authenticate {
        post("/api/v1/tasks/{id}/assign") {
            val userId = call.getUserId()
            val taskId = TaskId(call.parameters["id"]?.toInt() ?: error("Task ID required"))
            val request = call.receive<AssignTaskRequest>()

            call.respond(HttpStatusCode.OK, assignTaskUseCase(userId, taskId, UserId(request.assignedUserId)))
        }
    }
}
