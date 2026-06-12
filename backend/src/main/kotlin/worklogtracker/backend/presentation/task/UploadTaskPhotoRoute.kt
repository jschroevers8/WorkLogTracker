package worklogtracker.backend.presentation.task

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.task.UploadTaskPhotoUseCase
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.task.UploadTaskPhotoRequest

fun Route.uploadTaskPhotoRoute(uploadTaskPhotoUseCase: UploadTaskPhotoUseCase) {
    authenticate {
        post("/api/tasks/photos") {
            val userId = call.getUserId()
            val request = call.receive<UploadTaskPhotoRequest>()

            call.respond(
                HttpStatusCode.Created, uploadTaskPhotoUseCase(
                    userId = userId,
                    taskId = TaskId(request.taskId),
                    photoUrl = request.photoUrl
                )
            )
        }
    }
}
