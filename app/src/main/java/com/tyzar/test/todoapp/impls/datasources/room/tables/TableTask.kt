package com.tyzar.test.todoapp.impls.datasources.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tyzar.test.todoapp.core.formatter.formatDate
import com.tyzar.test.todoapp.core.formatter.parseDateTime
import com.tyzar.test.todoapp.core.formatter.saveDateFormat
import com.tyzar.test.todoapp.domain.entities.Task

@Entity(tableName = "task")
data class TableTask(
    @PrimaryKey val id: String,
    val title: String,
    val desc: String,
    val dateTime: String, //save format: yyyyMMddHHmm
    val isCompleted: Boolean
)

fun Task.fromDomain(): TableTask {
    return TableTask(
        id = id,
        title = title,
        desc = desc,
        isCompleted = isDone,
        dateTime = formatDate(dateTime, saveDateFormat)
    )
}

fun TableTask.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        desc = desc,
        isDone = isCompleted,
        dateTime = parseDateTime(dateTime, saveDateFormat)
    )
}
