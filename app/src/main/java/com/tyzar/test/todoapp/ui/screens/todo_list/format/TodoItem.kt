package com.tyzar.test.todoapp.ui.screens.todo_list.format

import com.tyzar.test.todoapp.domain.entities.Task
import java.time.LocalDate

sealed class TodoItem(open val key: String) {
    data class TodoHeader(override val key: String, val date: LocalDate) : TodoItem(key)
    data class TodoData(override val key: String, val task: Task) : TodoItem(key)
}