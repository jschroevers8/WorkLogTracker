package worklogtracker.backend.presentation.task
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.task.ListTasksUseCase
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.infrastructure.plugins.getUserId

fun Route.getTasksRoute(listTasksUseCase: ListTasksUseCase) {
    authenticate {
        get("/api/tasks") {
            val userId = call.getUserId()
            val projectId = call.parameters["projectId"]?.toInt()?.let(::ProjectId)
            val status = call.parameters["status"]?.let(TaskStatus::valueOf)

            call.respond(HttpStatusCode.OK, listTasksUseCase(userId, projectId, status))
        }
    }
}
