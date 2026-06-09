package worklogtracker.presentation.worklog

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.worklog.CreateManualWorkLogUseCase
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import java.time.LocalDateTime

fun Route.createWorkLogRoute(createManualWorkLogUseCase: CreateManualWorkLogUseCase) {
    authenticate {
        post("/api/worklogs") {
            val userId = call.getUserId()
            val request = call.receive<CreateWorkLogRequest>()

            call.respond(
                HttpStatusCode.Created, createManualWorkLogUseCase(
                    userId = userId,
                    taskId = TaskId(request.taskId),
                    startTime = LocalDateTime.parse(request.startTime),
                    endTime = LocalDateTime.parse(request.endTime),
                    notes = request.notes,
                    photoUrl = request.photoBase64, // For now, storing base64 as URL string or handle upload
                    latitude = request.latitude,
                    longitude = request.longitude
                )
            )
        }
    }
}
