package com.tyzar.test.todoapp.datasources

import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.core.errors.AppError
import com.tyzar.test.todoapp.domain.entities.Task
import java.time.LocalDateTime

interface TaskLocalData {
    suspend fun insert(task: Task): Either<AppError, Unit>

    suspend fun delete(taskId: String): Either<AppError, Unit>

    suspend fun update(task: Task): Either<AppError, Unit>

    suspend fun get(taskId: String): Task?

    suspend fun get(startDate: LocalDateTime, endDate: LocalDateTime): List<Task>
}