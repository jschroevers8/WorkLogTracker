package worklogtracker.backend.infrastructure.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import worklogtracker.backend.domain.exceptions.UnauthorizedException

fun Application.configureSecurity() {
    val jwtSecret = environment.config.property("jwt.secret").getString()

    install(Authentication) {
        jwt {
            realm = "worklog-tracker"
            val algorithm = Algorithm.HMAC256(jwtSecret)
            verifier(
                JWT
                    .require(algorithm)
                    .build(),
            )
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asInt()

                if (userId != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                throw UnauthorizedException("Invalid or missing token")
            }
        }
    }
}
