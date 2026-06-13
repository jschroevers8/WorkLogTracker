package worklogtracker.backend.presentation.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.task.UpdateTaskStatusUseCase
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.UpdateTaskStatusRequest

fun Route.updateTaskStatusRoute(updateTaskStatusUseCase: UpdateTaskStatusUseCase) {
    authenticate {
        post("/api/tasks/{id}/status") {
            val userId = call.getUserId()
            val taskId = TaskId(call.parameters["id"]?.toInt() ?: error("Task ID required"))
            val request = call.receive<UpdateTaskStatusRequest>()

            call.respond(HttpStatusCode.OK, updateTaskStatusUseCase(userId, taskId, TaskStatus.valueOf(request.status)))
        }
    }
}
