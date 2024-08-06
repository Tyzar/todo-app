package com.tyzar.test.todoapp.domain.entities

import kotlinx.datetime.LocalDateTime

data class Todo(
    val id: Int,
    val title: String,
    val desc: String,
    val dateTime: LocalDateTime,
    val isDone: Boolean = false
)