package com.tyzar.test.todoapp.ui.viewmodels.todolist

import com.tyzar.test.todoapp.domain.entities.Task
import java.time.LocalDate

enum class DeleteTaskStatus {
    Uninitiated, Progress, Error, Success
}

enum class CompleteTaskStatus {
    Uninitiated, Error, Success
}

data class ToDoListState(
    val dateGroup: Map<LocalDate, MutableList<Task>> = emptyMap(),
    val selectedTask: Task? = null,
    val deleteTaskStatus: DeleteTaskStatus = DeleteTaskStatus.Uninitiated,
    val completeTaskStatus: CompleteTaskStatus = CompleteTaskStatus.Uninitiated
)
