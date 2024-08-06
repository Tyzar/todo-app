package com.tyzar.test.todoapp.domain.entities

import java.time.LocalDateTime

data class Todo(
    val id: Int,
    val title: String,
    val desc: String,
    val dateTime: LocalDateTime,
    val isDone: Boolean = false
)