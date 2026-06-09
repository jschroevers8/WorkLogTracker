package worklogtracker.presentation.routes.auth
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.auth.LoginUserUseCase
import worklogtracker.domain.valueobjects.user.Email
import worklogtracker.presentation.dto.auth.LoginRequest

fun Route.loginRoute(loginUseCase: LoginUserUseCase) {
    post("/api/v1/auth/login") {
        val request = call.receive<LoginRequest>()

        call.respond(
            HttpStatusCode.OK,
            loginUseCase(Email(request.email), request.password),
        )
    }
}
