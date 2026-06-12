package worklogtracker.backend.domain.auth

import worklogtracker.backend.domain.valueobjects.user.UserId

interface TokenGeneratorInterface {
    fun generate(userId: UserId): String
}
