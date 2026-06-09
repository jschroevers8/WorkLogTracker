package worklogtracker.presentation.routes.notification
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.notification.GetUserNotificationsUseCase
import worklogtracker.infrastructure.plugins.getUserId

fun Route.getNotificationsRoute(getUserNotificationsUseCase: GetUserNotificationsUseCase) {
    authenticate {
        get("/api/v1/notifications") {
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
