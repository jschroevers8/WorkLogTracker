package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.UserEntity
import worklogtracker.backend.domain.entities.enums.UserRole
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.backend.domain.valueobjects.user.UserId

interface UserRepositoryInterface {
    suspend fun findById(id: UserId): UserEntity?
    suspend fun findByEmail(email: Email): UserEntity?
    suspend fun save(user: UserEntity): UserEntity
    suspend fun update(user: UserEntity): Boolean
    suspend fun delete(id: UserId): Boolean
    suspend fun findAll(role: UserRole? = null): List<UserEntity>
}

