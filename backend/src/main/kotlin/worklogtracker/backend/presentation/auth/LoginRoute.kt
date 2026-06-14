package worklogtracker.backend.presentation.auth

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.usecases.auth.LoginUserUseCase
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.shared.dto.auth.LoginRequest

fun Route.loginRoute(loginUseCase: LoginUserUseCase) {
    post("/api/auth/login") {
        val request = call.receive<LoginRequest>()

        call.respond(
            HttpStatusCode.OK,
            loginUseCase(Email(request.email), request.password),
        )
    }
}
