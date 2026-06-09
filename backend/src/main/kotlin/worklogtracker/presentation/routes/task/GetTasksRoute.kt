package worklogtracker.presentation.routes.task
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.task.ListTasksUseCase
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.infrastructure.plugins.getUserId

fun Route.getTasksRoute(listTasksUseCase: ListTasksUseCase) {
    authenticate {
        get("/api/v1/tasks") {
            val userId = call.getUserId()
            val projectId = call.parameters["projectId"]?.toInt()?.let(::ProjectId)
            val status = call.parameters["status"]?.let(TaskStatus::valueOf)

            call.respond(HttpStatusCode.OK, listTasksUseCase(userId, projectId, status))
        }
    }
}
