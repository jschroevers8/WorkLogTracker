package worklogtracker.backend.infrastructure.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.respond
import worklogtracker.backend.application.exceptions.ApplicationException
import worklogtracker.backend.application.exceptions.UserAuthenticationFailedException
import worklogtracker.backend.domain.exceptions.DuplicateEmailException
import worklogtracker.backend.domain.exceptions.InvalidEmailException
import worklogtracker.backend.domain.exceptions.InvalidTaskStatusTransitionException
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.exceptions.UserNotFoundException
import worklogtracker.shared.dto.common.ErrorResponse
import java.time.LocalDateTime

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<UserNotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(
                    error = "USER_NOT_FOUND",
                    message = cause.message ?: "User not found",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<UserAuthenticationFailedException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    error = "INVALID_CREDENTIALS",
                    message = cause.message ?: "Invalid email or password",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<DuplicateEmailException> { call, cause ->
            call.respond(
                HttpStatusCode.Conflict,
                ErrorResponse(
                    error = "DUPLICATE_EMAIL",
                    message = cause.message ?: "Email already registered",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<UnauthorizedException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden,
                ErrorResponse(
                    error = "UNAUTHORIZED",
                    message = cause.message ?: "Unauthorized action",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<InvalidTaskStatusTransitionException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "INVALID_STATUS_TRANSITION",
                    message = cause.message ?: "Invalid task status transition",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<InvalidEmailException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "INVALID_EMAIL",
                    message = cause.message ?: "Invalid email format",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<ApplicationException> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "OPERATION_FAILED",
                    message = cause.message ?: "Operation failed",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        exception<Exception> { call, cause ->
            cause.printStackTrace()
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "INTERNAL_SERVER_ERROR",
                    message = "An unexpected error occurred",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }
    }
}
