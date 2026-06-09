package worklogtracker.domain.entities

import worklogtracker.domain.entities.enums.ProjectStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Project Aggregate Root
 * 
 * Represents a project/scope that contains multiple tasks.
 * All tasks belong to exactly one project.
 * 
 * Domain Rules:
 * - StartDate must be before EndDate
 * - Only admins can create/modify projects
 * - Status transitions follow business rules
 */
data class ProjectEntity(
    val id: ProjectId? = null,
    val name: String,
    val description: String?,
    val status: ProjectStatus,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val createdById: UserId,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    
    init {
        if (startDate != null && endDate != null) {
            require(startDate < endDate) { "Start date must be before end date" }
        }
    }
    
    /**
     * Update project status with validation
     */
    fun updateStatus(newStatus: ProjectStatus): ProjectEntity {
        val validTransitions = mapOf(
            ProjectStatus.PLANNING to setOf(ProjectStatus.ACTIVE, ProjectStatus.ON_HOLD),
            ProjectStatus.ACTIVE to setOf(ProjectStatus.COMPLETED, ProjectStatus.ON_HOLD),
            ProjectStatus.ON_HOLD to setOf(ProjectStatus.ACTIVE),
            ProjectStatus.COMPLETED to setOf()
        )
        
        val allowed = validTransitions[status] ?: emptySet()
        require(newStatus in allowed) { 
            "Cannot transition from $status to $newStatus" 
        }
        
        return this.copy(status = newStatus, updatedAt = LocalDateTime.now())
    }

    /**
     * Check if project is currently active (accepting work)
     */
    fun isActive(): Boolean = status == ProjectStatus.ACTIVE
}

