package worklogtracker.backend.infrastructure.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.HttpStatusCode
import worklogtracker.shared.dto.common.ErrorResponse
import worklogtracker.backend.application.exceptions.UserAuthenticationFailedException
import io.ktor.server.response.respond
import worklogtracker.backend.application.exceptions.ApplicationException
import worklogtracker.backend.domain.exceptions.ActiveTimerAlreadyExistsException
import worklogtracker.backend.domain.exceptions.DuplicateEmailException
import worklogtracker.backend.domain.exceptions.FileSizeExceededException
import worklogtracker.backend.domain.exceptions.InvalidEmailException
import worklogtracker.backend.domain.exceptions.InvalidFileTypeException
import worklogtracker.backend.domain.exceptions.InvalidPasswordException
import worklogtracker.backend.domain.exceptions.InvalidTaskStatusTransitionException
import worklogtracker.backend.domain.exceptions.PasswordVerificationFailedException
import worklogtracker.backend.domain.exceptions.TaskNotAssignedToUserException
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.exceptions.UserNotFoundException
import java.time.LocalDateTime

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<UserNotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(
                    error = "USER_NOT_FOUND",
                    message = cause.message ?: "User not found",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }

        exception<UserAuthenticationFailedException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    error = "INVALID_CREDENTIALS",
                    message = cause.message ?: "Invalid email or password",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<DuplicateEmailException> { call, cause ->
            call.respond(
                HttpStatusCode.Conflict,
                ErrorResponse(
                    error = "DUPLICATE_EMAIL",
                    message = cause.message ?: "Email already registered",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<PasswordVerificationFailedException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    error = "INVALID_CREDENTIALS",
                    message = "Invalid email or password",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<UnauthorizedException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden,
                ErrorResponse(
                    error = "UNAUTHORIZED",
                    message = cause.message ?: "Unauthorized action",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<InvalidTaskStatusTransitionException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "INVALID_STATUS_TRANSITION",
                    message = cause.message ?: "Invalid task status transition",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<TaskNotAssignedToUserException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden,
                ErrorResponse(
                    error = "TASK_NOT_ASSIGNED",
                    message = cause.message ?: "Task is not assigned to user",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<ActiveTimerAlreadyExistsException> { call, cause ->
            call.respond(
                HttpStatusCode.Conflict,
                ErrorResponse(
                    error = "ACTIVE_TIMER_EXISTS",
                    message = cause.message ?: "User already has an active timer",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<InvalidEmailException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "INVALID_EMAIL",
                    message = cause.message ?: "Invalid email format",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        // Domain exceptions - Invalid password
        exception<InvalidPasswordException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "INVALID_PASSWORD",
                    message = cause.message ?: "Password does not meet requirements",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<FileSizeExceededException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "FILE_TOO_LARGE",
                    message = cause.message ?: "File size exceeds maximum limit",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<InvalidFileTypeException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "INVALID_FILE_TYPE",
                    message = cause.message ?: "File type not allowed",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<ApplicationException> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "OPERATION_FAILED",
                    message = cause.message ?: "Operation failed",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
        
        exception<Exception> { call, cause ->
            cause.printStackTrace()
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "INTERNAL_SERVER_ERROR",
                    message = "An unexpected error occurred",
                    timestamp = LocalDateTime.now().toString()
                )
            )
        }
    }
}

