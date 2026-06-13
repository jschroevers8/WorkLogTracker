package worklogtracker.frontend.presentation.task

import worklogtracker.frontend.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.frontend.repositories.TaskRepository

class TaskViewModel(
    private val taskRepository: TaskRepository
) : BaseViewModel<TaskUiState>(TaskUiState()) {

    fun loadTasks() {
        launchWithErrorHandling {
            val tasks = taskRepository.getTasks()

            val taskList = tasks.map { response ->
                TaskItem(
                    id = response.id?.toString() ?: "",
                    title = response.title,
                    description = response.description ?: "",
                    status = response.status,
                )
            }

            _uiState = uiState.copy(tasks = taskList)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
