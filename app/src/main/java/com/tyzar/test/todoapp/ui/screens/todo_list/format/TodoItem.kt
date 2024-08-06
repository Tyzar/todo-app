package com.tyzar.test.todoapp.ui.screens.todo_list.format

import com.tyzar.test.todoapp.domain.entities.Todo
import java.time.LocalDateTime

sealed class TodoItem(open val key: Int) {
    data class TodoHeader(override val key: Int, val dateTime: LocalDateTime) : TodoItem(key)
    data class TodoData(override val key: Int, val todo: Todo) : TodoItem(key)
}