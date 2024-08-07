package com.tyzar.test.todoapp.ui.viewmodels.todolist

import com.tyzar.test.todoapp.domain.entities.Task
import java.time.LocalDate
import java.time.LocalTime

sealed class ToDoListEvent {
    data object Load : ToDoListEvent()
    data object Refresh : ToDoListEvent()
    data class SelectTask(val task: Task?) : ToDoListEvent()
    data class DeleteTask(val taskId: String) : ToDoListEvent()
    data class CompleteTask(val taskId: String) : ToDoListEvent()
    data class RescheduleTask(val taskId: String, val date: LocalDate, val time: LocalTime?) :
        ToDoListEvent()
}