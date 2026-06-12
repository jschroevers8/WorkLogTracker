package worklogtracker.frontend.presentation.task

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState

data class TaskUiState(
    val tasks: List<TaskItem> = emptyList(),
    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState

data class TaskItem(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val priority: String
)
