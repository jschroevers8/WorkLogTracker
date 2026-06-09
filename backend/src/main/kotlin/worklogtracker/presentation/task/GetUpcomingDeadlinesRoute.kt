package worklogtracker.presentation.task
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.task.GetUpcomingDeadlinesUseCase

fun Route.getUpcomingDeadlinesRoute(getUpcomingDeadlinesUseCase: GetUpcomingDeadlinesUseCase) {
    authenticate {
        get("/api/tasks/upcoming-deadlines") {
            val daysAhead = call.parameters["days"]?.toIntOrNull() ?: 7
            call.respond(HttpStatusCode.OK, getUpcomingDeadlinesUseCase(daysAhead))
        }
    }
}
