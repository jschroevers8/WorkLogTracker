package worklogtracker.domain.auth

import worklogtracker.domain.valueobjects.user.UserId

interface TokenGeneratorInterface {
    fun generate(userId: UserId): String
}
