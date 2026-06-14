package worklogtracker.backend.infrastructure.plugins

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.valueobjects.user.UserId

fun ApplicationCall.getUserId(): UserId {
    val principal = authentication.principal<JWTPrincipal>()
    val userId =
        principal?.payload?.getClaim("userId")?.asInt()
            ?: throw UnauthorizedException("No user ID in token")
    return UserId(userId)
}
