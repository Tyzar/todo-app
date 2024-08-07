package com.tyzar.test.todoapp.ui.viewmodels.reschedule_task

import java.time.LocalDate
import java.time.LocalTime

sealed class RescheduleTaskEvent {
    data class LoadTask(val taskId: String) : RescheduleTaskEvent()
    data class UpdateDate(val localDate: LocalDate) : RescheduleTaskEvent()
    data class UpdateTime(val localTime: LocalTime) : RescheduleTaskEvent()
    data object Reschedule : RescheduleTaskEvent()
}