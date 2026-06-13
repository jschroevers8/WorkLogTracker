package worklogtracker.backend.presentation.user

import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.backend.application.mappers.toUserResponse
import worklogtracker.backend.domain.repositories.UserRepositoryInterface

fun Route.getUsersRoute(userRepository: UserRepositoryInterface) {
    get("/api/users") {
        val users = userRepository.findAll(null).map { it.toUserResponse() }
        call.respond(users)
    }
}
