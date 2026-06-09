package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.UserEntity
import worklogtracker.domain.entities.enums.UserRole
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.user.Email
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.hydrators.hydrateUser
import worklogtracker.infrastructure.tables.UserTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

class UserRepository : UserRepositoryInterface {

    override suspend fun findById(id: UserId): UserEntity? =
        transaction {
            UserTable
                .selectAll()
                .where { UserTable.id eq id.value }
                .map { it.hydrateUser() }
                .singleOrNull()
        }

    override suspend fun findByEmail(email: Email): UserEntity? =
        transaction {
            UserTable
                .selectAll()
                .where { UserTable.email eq email.value }
                .map { it.hydrateUser() }
                .singleOrNull()
        }

    override suspend fun save(user: UserEntity): UserEntity =
        transaction {
            val now = Instant.now().toEpochMilli()

            UserTable.insert {
                it[email] = user.email.value
                it[passwordHash] = user.passwordHash.hash
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[role] = user.role.name
                it[createdAt] = now
                it[updatedAt] = now
            }

            user.copy(
                createdAt = Instant.ofEpochMilli(now)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
                updatedAt = Instant.ofEpochMilli(now)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            )
        }

    override suspend fun update(user: UserEntity): Boolean =
        transaction {
            val now = Instant.now().toEpochMilli()

            UserTable.update({ UserTable.id eq user.id!!.value }) {
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[role] = user.role.name
                it[updatedAt] = now
            } > 0
        }

    override suspend fun delete(id: UserId): Boolean =
        transaction {
            UserTable.deleteWhere { UserTable.id eq id.value } > 0
        }

    override suspend fun findAll(role: UserRole?): List<UserEntity> =
        transaction {
            if (role != null) {
                UserTable
                    .selectAll()
                    .where { UserTable.role eq role.name }
                    .map { it.hydrateUser() }
            } else {
                UserTable
                    .selectAll()
                    .map { it.hydrateUser() }
            }
        }
}