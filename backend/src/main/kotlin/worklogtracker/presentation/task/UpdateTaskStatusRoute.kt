package worklogtracker.presentation.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.task.UpdateTaskStatusUseCase
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.UpdateTaskStatusRequest

fun Route.updateTaskStatusRoute(updateTaskStatusUseCase: UpdateTaskStatusUseCase) {
    authenticate {
        put("/api/tasks/{id}/status") {
            val userId = call.getUserId()
            val taskId = TaskId(call.parameters["id"]?.toInt() ?: error("Task ID required"))
            val request = call.receive<UpdateTaskStatusRequest>()

            call.respond(HttpStatusCode.OK, updateTaskStatusUseCase(userId, taskId, TaskStatus.valueOf(request.status)))
        }
    }
}
