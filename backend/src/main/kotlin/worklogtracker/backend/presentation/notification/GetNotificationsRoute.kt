package worklogtracker.backend.presentation.notification
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.notification.GetUserNotificationsUseCase
import worklogtracker.backend.infrastructure.plugins.getUserId

fun Route.getNotificationsRoute(getUserNotificationsUseCase: GetUserNotificationsUseCase) {
    authenticate {
        get("/api/notifications") {
            call.respond(
                HttpStatusCode.OK,
                getUserNotificationsUseCase(
                    call.getUserId(),
                    call.parameters["unreadOnly"]?.toBoolean() ?: false,
                )
            )
        }
    }
}
