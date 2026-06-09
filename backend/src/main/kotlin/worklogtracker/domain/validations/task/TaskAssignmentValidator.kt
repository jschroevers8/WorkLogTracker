package worklogtracker.domain.validations.task

import worklogtracker.domain.entities.TaskEntity
import worklogtracker.domain.exceptions.TaskNotAssignedToUserException
import worklogtracker.domain.valueobjects.user.UserId

class TaskAssignmentValidator {
    
    fun validate(task: TaskEntity, userId: UserId) {
        if (task.assignedUserId != userId) {
            throw TaskNotAssignedToUserException(userId.value.toString(), task.id!!.value.toString())
        }
    }
}

