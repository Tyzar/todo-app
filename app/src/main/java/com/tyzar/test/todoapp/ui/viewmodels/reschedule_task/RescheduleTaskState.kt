package com.tyzar.test.todoapp.ui.viewmodels.reschedule_task

import com.tyzar.test.todoapp.domain.entities.Task

enum class RescheduleTaskStatus {
    Uninitiated, Error, Success
}

data class RescheduleTaskState(
    val task: Task? = null,
    val rescheduleStatus: RescheduleTaskStatus = RescheduleTaskStatus.Uninitiated
)