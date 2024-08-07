package com.tyzar.test.todoapp.impls.repositories

import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.core.errors.AppError
import com.tyzar.test.todoapp.datasources.TaskLocalData
import com.tyzar.test.todoapp.domain.entities.Task
import com.tyzar.test.todoapp.domain.repositories.TaskRepository
import java.time.LocalDateTime
import javax.inject.Inject

class TaskRepoImpl @Inject constructor(private val taskLocalData: TaskLocalData) : TaskRepository {
    override suspend fun addTask(task: Task): Either<AppError, Unit> {
        return taskLocalData.insert(task)
    }

    override suspend fun setComplete(taskId: String): Either<AppError, Unit> {
        val task = getTask(taskId) ?: return Either.Left(AppError("Task not found"))
        val doneTask = task.copy(isDone = true)
        return taskLocalData.update(doneTask)
    }

    override suspend fun deleteTask(taskId: String): Either<AppError, Unit> {
        return taskLocalData.delete(taskId)
    }

    override suspend fun rescheduleTask(
        taskId: String,
        newDateTime: LocalDateTime
    ): Either<AppError, Unit> {
        val task = getTask(taskId) ?: return Either.Left(AppError("Task not found"))
        val rescheduledTask = task.copy(
            dateTime = newDateTime
        )
        return taskLocalData.update(rescheduledTask)
    }

    override suspend fun showTasks(startDate: LocalDateTime, endDate: LocalDateTime): List<Task> {
        return taskLocalData.get(startDate, endDate)
    }

    override suspend fun getTask(taskId: String): Task? {
        return taskLocalData.get(taskId)
    }
}