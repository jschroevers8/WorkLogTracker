package worklogtracker.presentation.routes.notification

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.notification.MarkNotificationAsReadUseCase
import worklogtracker.domain.valueobjects.notification.NotificationId
import worklogtracker.infrastructure.plugins.getUserId

fun Route.markNotificationReadRoute(markNotificationAsReadUseCase: MarkNotificationAsReadUseCase) {
    authenticate {
        put("/api/v1/notifications/{id}/read") {
            val notificationId = NotificationId(call.parameters["id"]?.toInt() ?: error("Notification ID required"))

            call.respond(
                HttpStatusCode.OK,
                mapOf("success" to markNotificationAsReadUseCase(
                    call.getUserId(),
                    notificationId,
                ))
            )
        }
    }
}
