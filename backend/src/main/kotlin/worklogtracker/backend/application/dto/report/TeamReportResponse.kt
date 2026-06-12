package worklogtracker.backend.application.dto.report
import kotlinx.serialization.Serializable
@Serializable
data class TeamReportResponse(
    val teamMembers: Int,
    val totalTasksAssigned: Int,
    val totalTasksCompleted: Int,
    val averageHoursPerTask: Double,
    val period: String
)
