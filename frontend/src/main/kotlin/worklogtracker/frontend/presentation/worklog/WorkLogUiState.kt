package worklogtracker.frontend.presentation.worklog

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState

data class WorkLogUiState(
    val tasks: List<TaskItem> = emptyList(),
    val selectedTaskAssignmentId: Int? = null,
    val hours: String = "",
    val description: String = "",
    val photoBase64: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val success: Boolean = false,
    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState

data class TaskItem(
    val id: Int,
    val title: String,
    val assignmentId: Int? = null
)
