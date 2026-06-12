package worklogtracker.backend.domain.factories

import worklogtracker.backend.domain.entities.ProjectEntity
import worklogtracker.backend.domain.entities.enums.ProjectStatus
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDate
import java.time.LocalDateTime

class ProjectFactory {
    
    fun create(
        name: String,
        description: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        createdById: UserId
    ): ProjectEntity {
        return ProjectEntity(
            name = name,
            description = description,
            status = ProjectStatus.PLANNING,
            startDate = startDate,
            endDate = endDate,
            createdById = createdById,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

