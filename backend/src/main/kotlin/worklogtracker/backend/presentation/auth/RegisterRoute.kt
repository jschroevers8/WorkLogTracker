package worklogtracker.backend.presentation.auth

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.auth.RegisterUserUseCase
import worklogtracker.shared.dto.auth.RegisterUserRequest

fun Route.registerRoute(registerUseCase: RegisterUserUseCase) {
    post("/api/auth/register") {
        val request = call.receive<RegisterUserRequest>()

        call.respond(
            HttpStatusCode.Created,
            registerUseCase(request.email, request.password, request.firstName, request.lastName),
        )
    }
}
