package worklogtracker.application.dto.report

import kotlinx.serialization.Serializable

@Serializable
data class UserReportResponse(
    val userId: Int,
    val userName: String,
    val totalHours: Double,
    val taskCount: Int,
    val completedTasks: Int,
    val averageHoursPerTask: Double,
    val period: String
)
