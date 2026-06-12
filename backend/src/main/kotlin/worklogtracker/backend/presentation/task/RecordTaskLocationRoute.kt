package worklogtracker.backend.presentation.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.task.RecordTaskLocationUseCase
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.shared.dto.task.RecordTaskLocationRequest

fun Route.recordTaskLocationRoute(recordTaskLocationUseCase: RecordTaskLocationUseCase) {
    authenticate {
        post("/api/tasks/locations") {
            val request = call.receive<RecordTaskLocationRequest>()

            call.respond(
                HttpStatusCode.Created, recordTaskLocationUseCase(
                    taskId = TaskId(request.taskId),
                    latitude = request.latitude,
                    longitude = request.longitude
                )
            )
        }
    }
}
