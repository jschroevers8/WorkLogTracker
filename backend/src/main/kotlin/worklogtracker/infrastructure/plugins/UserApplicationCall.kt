package worklogtracker.infrastructure.plugins

import worklogtracker.domain.exceptions.UnauthorizedException
import worklogtracker.domain.valueobjects.user.UserId
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal

fun ApplicationCall.getUserId(): UserId {
    val principal = authentication.principal<JWTPrincipal>()
    val userId = principal?.payload?.getClaim("userId")?.asInt()
        ?: throw UnauthorizedException("No user ID in token")
    return UserId(userId)

}
