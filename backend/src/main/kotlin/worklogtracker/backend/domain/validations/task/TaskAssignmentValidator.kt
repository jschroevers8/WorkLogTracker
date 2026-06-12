package worklogtracker.backend.domain.validations.task

import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.exceptions.TaskNotAssignedToUserException
import worklogtracker.backend.domain.valueobjects.user.UserId

class TaskAssignmentValidator {
    
    fun validate(task: TaskEntity, userId: UserId) {
        if (task.assignedUserId != userId) {
            throw TaskNotAssignedToUserException(userId.value.toString(), task.id!!.value.toString())
        }
    }
}

