package worklogtracker.domain.repositories

import worklogtracker.domain.entities.UserEntity
import worklogtracker.domain.entities.enums.UserRole
import worklogtracker.domain.valueobjects.user.Email
import worklogtracker.domain.valueobjects.user.UserId

interface UserRepositoryInterface {
    suspend fun findById(id: UserId): UserEntity?
    suspend fun findByEmail(email: Email): UserEntity?
    suspend fun save(user: UserEntity): UserEntity
    suspend fun update(user: UserEntity): Boolean
    suspend fun delete(id: UserId): Boolean
    suspend fun findAll(role: UserRole? = null): List<UserEntity>
}

