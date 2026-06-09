package worklogtracker.presentation.sync
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.sync.GetPendingSyncsUseCase
import worklogtracker.infrastructure.plugins.getUserId

fun Route.getPendingSyncsRoute(getPendingSyncsUseCase: GetPendingSyncsUseCase) {
    authenticate {
        get("/api/sync/pending") {
            val userId = call.getUserId()
            call.respond(HttpStatusCode.OK, getPendingSyncsUseCase(userId))
        }
    }
}
