package worklogtracker.application.dto.report

import kotlinx.serialization.Serializable

@Serializable
data class ProjectReportResponse(
    val projectId: Int,
    val projectName: String,
    val taskCount: Int,
    val completedTasks: Int,
    val totalEstimatedHours: Double,
    val totalActualHours: Double,
    val status: String,
    val progress: Double
)
