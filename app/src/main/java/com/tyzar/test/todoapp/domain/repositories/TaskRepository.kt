package com.tyzar.test.todoapp.domain.repositories

import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.core.errors.AppError
import com.tyzar.test.todoapp.domain.entities.Task
import java.time.LocalDateTime

interface TaskRepository {
    suspend fun addTask(task: Task): Either<AppError, Unit>

    suspend fun setComplete(taskId: String): Either<AppError, Unit>

    suspend fun deleteTask(taskId: String): Either<AppError, Unit>

    suspend fun rescheduleTask(taskId: String, newDateTime: LocalDateTime): Either<AppError, Unit>

    suspend fun showTasks(startDate: LocalDateTime, endDate: LocalDateTime): List<Task>

    suspend fun getTask(taskId: String): Task?
}