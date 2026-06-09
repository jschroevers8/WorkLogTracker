package worklogtracker.presentation.worklog

import worklogtracker.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.repositories.TaskRepository
import worklogtracker.repositories.WorkLogRepository
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import java.time.LocalDateTime

class WorkLogViewModel(
    private val taskRepository: TaskRepository,
    private val workLogRepository: WorkLogRepository
) : BaseViewModel<WorkLogUiState>(WorkLogUiState()) {

    init {
        loadTasks()
    }

    fun loadTasks() {
        launchWithErrorHandling {
            val tasks = taskRepository.getTasks()
            val taskList = tasks.map { TaskItem(it.id ?: 0, it.title) }
            _uiState = uiState.copy(tasks = taskList)
        }
    }

    fun setSelectedTask(taskId: Int) {
        _uiState = uiState.copy(selectedTaskId = taskId)
    }

    fun onTaskSelected(taskId: Int) {
        _uiState = uiState.copy(selectedTaskId = taskId)
    }

    fun onNotesChanged(notes: String) {
        _uiState = uiState.copy(notes = notes)
    }

    fun onLocationCaptured(lat: Double, lon: Double) {
        _uiState = uiState.copy(latitude = lat, longitude = lon)
    }

    fun onPhotoCaptured(base64: String) {
        _uiState = uiState.copy(photoBase64 = base64)
    }

    fun submitWorkLog() {
        val taskId = uiState.selectedTaskId ?: return
        
        launchWithErrorHandling {
            setLoading(true)
            val now = LocalDateTime.now().toString()
            val request = CreateWorkLogRequest(
                taskId = taskId,
                startTime = now,
                endTime = now,
                notes = uiState.notes,
                photoBase64 = uiState.photoBase64,
                latitude = uiState.latitude,
                longitude = uiState.longitude
            )
            workLogRepository.createWorkLog(request)
            _uiState = uiState.copy(
                success = true,
                notes = "",
                photoBase64 = null,
                latitude = null,
                longitude = null,
                selectedTaskId = null
            )
            setLoading(false)
        }
    }

    fun resetSuccess() {
        _uiState = uiState.copy(success = false)
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
