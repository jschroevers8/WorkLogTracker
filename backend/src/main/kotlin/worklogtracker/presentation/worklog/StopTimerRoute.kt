package worklogtracker.presentation.worklog
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.worklog.StopTimerUseCase
import worklogtracker.domain.valueobjects.timer.TimerSessionId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.worklog.StopTimerRequest

fun Route.stopTimerRoute(stopTimerUseCase: StopTimerUseCase) {
    authenticate {
        post("/api/worklogs/timer/stop") {
            val userId = call.getUserId()
            val request = call.receive<StopTimerRequest>()
            call.respond(HttpStatusCode.OK, stopTimerUseCase(userId, TimerSessionId(request.sessionId), request.notes))
        }
    }
}
