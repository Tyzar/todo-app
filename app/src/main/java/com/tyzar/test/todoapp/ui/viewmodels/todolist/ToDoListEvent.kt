package com.tyzar.test.todoapp.ui.viewmodels.todolist

import com.tyzar.test.todoapp.domain.entities.Task

sealed class ToDoListEvent {
    data object Load : ToDoListEvent()
    data object Refresh : ToDoListEvent()
    data class SelectTask(val task: Task?) : ToDoListEvent()
    data class DeleteTask(val task: Task) : ToDoListEvent()
    data class CompleteTask(val task: Task) : ToDoListEvent()
}