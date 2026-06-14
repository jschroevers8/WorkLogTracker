package worklogtracker.frontend.presentation.task

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState
import worklogtracker.frontend.presentation.task.item.TaskItem

data class TaskUiState(
    val tasks: List<TaskItem> = emptyList(),
    override val loading: Boolean = false,
    override val error: String? = null,
) : BaseUiState
