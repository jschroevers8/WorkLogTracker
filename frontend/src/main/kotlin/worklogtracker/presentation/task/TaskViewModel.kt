package worklogtracker.presentation.task

import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.TaskRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class TaskViewModel(
    private val taskRepository: TaskRepository
) : BaseViewModel<TaskUiState>(TaskUiState()) {

    init {
        loadTasks()
    }

    fun loadTasks() {
        launchWithErrorHandling {
            val tasksJson = taskRepository.getTasks()
            val json = Json { ignoreUnknownKeys = true }
            val element = json.parseToJsonElement(tasksJson)

            val taskList = element.jsonArray.map {
                val obj = it.jsonObject
                TaskItem(
                    id = obj["id"]?.jsonObject?.get("value")?.jsonPrimitive?.content ?: "",
                    title = obj["title"]?.jsonPrimitive?.content ?: "",
                    description = obj["description"]?.jsonPrimitive?.content ?: "",
                    status = obj["status"]?.jsonPrimitive?.content ?: "",
                    priority = obj["priority"]?.jsonPrimitive?.content ?: ""
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
