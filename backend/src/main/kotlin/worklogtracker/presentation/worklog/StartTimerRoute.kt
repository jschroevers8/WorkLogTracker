package worklogtracker.presentation.worklog
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.worklog.StartTimerUseCase
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.worklog.StartTimerRequest

fun Route.startTimerRoute(startTimerUseCase: StartTimerUseCase) {
    authenticate {
        post("/api/worklogs/timer/start") {
            val userId = call.getUserId()
            val request = call.receive<StartTimerRequest>()
            call.respond(HttpStatusCode.Created, startTimerUseCase(userId, TaskId(request.taskId)))
        }
    }
}
