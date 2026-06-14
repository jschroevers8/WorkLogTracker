package worklogtracker.backend.infrastructure.hydrators

import worklogtracker.backend.domain.entities.UserEntity
import worklogtracker.backend.domain.entities.enums.UserRole
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.backend.domain.valueobjects.user.Password
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.UserTable
import org.jetbrains.exposed.sql.ResultRow
import kotlinx.datetime.toJavaLocalDateTime

fun ResultRow.hydrateUser() =
    UserEntity(
        id = UserId(this[UserTable.id]),
        email = Email(this[UserTable.email]),
        passwordHash = Password.Companion.fromHash(
            this[UserTable.passwordHash]
        ),
        firstName = this[UserTable.firstName],
        lastName = this[UserTable.lastName],
        role = UserRole.valueOf(this[UserTable.role]),
        createdAt = this[UserTable.createdAt].toJavaLocalDateTime(),
        updatedAt = this[UserTable.updatedAt].toJavaLocalDateTime()
    )