package worklogtracker.frontend.presentation.worklog

data class TaskItem(
    val id: Int,
    val title: String,
    val assignmentId: Int? = null,
)
