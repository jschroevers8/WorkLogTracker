package worklogtracker.backend.infrastructure.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import worklogtracker.backend.domain.auth.TokenGeneratorInterface
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.util.*
import kotlin.time.Duration.Companion.hours

class JwtTokenGenerator(
    private val jwtSecret: String,
) : TokenGeneratorInterface {
    override fun generate(userId: UserId): String =
        JWT
            .create()
            .withClaim("userId", userId.value)
            .withExpiresAt(Date(System.currentTimeMillis() + 24.hours.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(jwtSecret))
}
