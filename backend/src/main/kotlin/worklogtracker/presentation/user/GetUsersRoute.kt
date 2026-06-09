package worklogtracker.presentation.routes.user

import io.ktor.server.response.*
import io.ktor.server.routing.*
import worklogtracker.application.mappers.toUserResponse
import worklogtracker.domain.repositories.UserRepositoryInterface

fun Route.getUsersRoute(userRepository: UserRepositoryInterface) {
    get("/api/v1/users") {
        val users = userRepository.findAll(null).map { it.toUserResponse() }
        call.respond(users)
    }
}
