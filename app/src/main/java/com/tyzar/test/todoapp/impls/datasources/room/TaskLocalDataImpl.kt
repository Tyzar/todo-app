package com.tyzar.test.todoapp.impls.datasources.room

import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.core.errors.AppError
import com.tyzar.test.todoapp.core.formatter.formatDate
import com.tyzar.test.todoapp.core.formatter.saveDateFormat
import com.tyzar.test.todoapp.datasources.TaskLocalData
import com.tyzar.test.todoapp.domain.entities.Task
import com.tyzar.test.todoapp.impls.datasources.room.tables.fromDomain
import com.tyzar.test.todoapp.impls.datasources.room.tables.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class TaskLocalDataImpl @Inject constructor(appDb: AppDb) : TaskLocalData {

    private val dao = appDb.taskDao()

    override suspend fun insert(task: Task): Either<AppError, Unit> = withContext(Dispatchers.IO) {
        try {
            dao.insert(task.fromDomain())
            return@withContext Either.Right(Unit)
        } catch (e: Exception) {
            return@withContext Either.Left(AppError("Failed to save task"))
        }
    }

    override suspend fun delete(taskId: String): Either<AppError, Unit> =
        withContext(Dispatchers.IO) {
            try {
                dao.delete(taskId)
                return@withContext Either.Right(Unit)
            } catch (e: Exception) {
                return@withContext Either.Left(AppError("Failed to delete task"))
            }
        }

    override suspend fun update(task: Task): Either<AppError, Unit> = withContext(Dispatchers.IO) {
        try {
            dao.update(task.fromDomain())
            return@withContext Either.Right(Unit)
        } catch (e: Exception) {
            return@withContext Either.Left(AppError("Failed to update task"))
        }
    }

    override suspend fun get(taskId: String): Task? = withContext(Dispatchers.IO) {
        try {
            val tTask = dao.get(taskId) ?: return@withContext null

            return@withContext tTask.toDomain()
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun get(startDate: LocalDateTime, endDate: LocalDateTime): List<Task> =
        withContext(Dispatchers.IO) {
            try {
                val start = formatDate(startDate, saveDateFormat)
                val end = formatDate(endDate, saveDateFormat)

                val tTasks = dao.get(start, end)

                return@withContext tTasks.map {
                    it.toDomain()
                }
            } catch (e: Exception) {
                return@withContext emptyList()
            }
        }
}