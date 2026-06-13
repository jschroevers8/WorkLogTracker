package worklogtracker.frontend.presentation.worklog

import worklogtracker.frontend.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.frontend.repositories.TaskRepository
import worklogtracker.frontend.repositories.WorkLogRepository
import worklogtracker.shared.dto.task.UpdateTaskStatusRequest
import worklogtracker.shared.dto.task.RecordTaskLocationRequest
import worklogtracker.shared.dto.task.UploadTaskPhotoRequest
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest

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
            val taskList = tasks
                .filter { it.status != "COMPLETED" && it.status != "CANCELLED" }
                .map { TaskItem(it.id ?: 0, it.title, it.assignmentId) }
            _uiState = uiState.copy(tasks = taskList)
        }
    }

    fun onTaskAssignmentSelected(id: Int) {
        _uiState = uiState.copy(selectedTaskAssignmentId = id)
    }

    fun onHoursChanged(hours: String) {
        _uiState = uiState.copy(hours = hours)
    }

    fun onDescriptionChanged(description: String) {
        _uiState = uiState.copy(description = description)
    }

    fun onLocationCaptured(lat: Double, lon: Double) {
        _uiState = uiState.copy(latitude = lat, longitude = lon)
    }

    fun onPhotoCaptured(base64: String) {
        _uiState = uiState.copy(photoBase64 = base64)
    }

    fun submitWorkLog() {
        val taskId = uiState.selectedTaskAssignmentId ?: return
        val selectedTask = uiState.tasks.find { it.id == taskId } ?: return
        val assignmentId = selectedTask.assignmentId ?: return
        val hours = uiState.hours.toDoubleOrNull() ?: return
        
        launchWithErrorHandling {
            setLoading(true)
            val request = CreateWorkLogRequest(
                taskAssignmentId = assignmentId,
                hours = hours,
                description = uiState.description
            )
            workLogRepository.createWorkLog(request)
            
            uiState.photoBase64?.let { 
                 taskRepository.uploadPhoto(UploadTaskPhotoRequest(taskId, it)) 
            }
            
            if (uiState.latitude != null && uiState.longitude != null) {
                taskRepository.recordLocation(RecordTaskLocationRequest(taskId, uiState.latitude!!, uiState.longitude!!))
            }
            
            // Mark task as COMPLETED
            taskRepository.updateTaskStatus(taskId.toString(), UpdateTaskStatusRequest("COMPLETED"))

            _uiState = uiState.copy(
                success = true,
                hours = "",
                description = "",
                photoBase64 = null,
                latitude = null,
                longitude = null,
                selectedTaskAssignmentId = null
            )
            loadTasks()
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
