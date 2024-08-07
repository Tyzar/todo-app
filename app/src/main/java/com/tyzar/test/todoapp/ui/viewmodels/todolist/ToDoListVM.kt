package com.tyzar.test.todoapp.ui.viewmodels.todolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokotogi.mantra.compose.paging.controller.MtrPageController
import com.nokotogi.mantra.compose.paging.states.MtrPageState
import com.nokotogi.mantra.compose.paging.states.PageResult
import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.core.errors.AppError
import com.tyzar.test.todoapp.domain.entities.Task
import com.tyzar.test.todoapp.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ToDoListVM @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val startDate = LocalDate.now()

    private val mToDoListState = MutableStateFlow(ToDoListState())
    val toDoListState = mToDoListState.asStateFlow()

    private val pagingController = MtrPageController(
        state = MtrPageState(pageSize = 7, pageKey = 0),
        setNextPageFunc = { key, _, _ ->
            (key ?: 0) + 7
        },
        loadPageFunc = { key, _ ->
            loadPage(key)
        }
    )

    init {
        notify(ToDoListEvent.Load)
    }

    fun notify(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.CompleteTask -> handleCompleteTask(event.task)
            is ToDoListEvent.DeleteTask -> handleDeleteTask(event.task)
            ToDoListEvent.Load -> {
                viewModelScope.launch {
                    pagingController.loadPage()
                }
            }

            ToDoListEvent.Refresh -> {
                viewModelScope.launch {
                    mToDoListState.value = ToDoListState()
                    pagingController.refreshPage()
                }
            }

            is ToDoListEvent.SelectTask -> {
                mToDoListState.value = mToDoListState.value.copy(
                    selectedTask = event.task
                )
            }
        }
    }

    private fun handleCompleteTask(task: Task) {
        viewModelScope.launch {
            val dateGroup = mToDoListState.value.dateGroup.toMutableMap()
            val date = task.dateTime.toLocalDate()
            val dateList = mutableListOf<Task>().apply { addAll(dateGroup[date]!!) }
            val selectedTaskIdx = dateList.indexOfFirst { e -> e.id == task.id }
            if (selectedTaskIdx == -1) {
                return@launch
            }

            dateList[selectedTaskIdx] = dateList[selectedTaskIdx].copy(
                isDone = true
            )
            dateGroup[date] = dateList

            if (mToDoListState.value.dateGroup == dateGroup) {
                Log.d("Fakkk", "Seriously it is same??")
            }

            mToDoListState.value = mToDoListState.value.copy(
                dateGroup = dateGroup
            )

            val result = taskRepository.setComplete(task.id)
            when (result) {
                is Either.Left -> mToDoListState.value = mToDoListState.value.copy(
                    completeTaskStatus = CompleteTaskStatus.Error
                )

                is Either.Right -> {
                    mToDoListState.value = mToDoListState.value.copy(
                        completeTaskStatus = CompleteTaskStatus.Success
                    )
                }
            }
        }
    }

    private suspend fun loadPage(key: Int?): PageResult<AppError, List<Task>> {
        try {
            val start = if (key == null) startDate else startDate.plusDays(key.toLong())
            val end = start.plusDays(6)
            val result =
                taskRepository.showTasks(
                    startDate = LocalDateTime.of(start, LocalTime.of(0, 0)),
                    endDate = LocalDateTime.of(end, LocalTime.of(0, 0))
                )

            //update date group
            val dateGroup = mToDoListState.value.dateGroup.toMutableMap()
            for (i in 0..6) {
                dateGroup[start.plusDays(i.toLong())] = mutableListOf()
            }

            for (task in result) {
                if (!dateGroup.containsKey(task.dateTime.toLocalDate())) {
                    val mTasks = mutableListOf<Task>()
                    mTasks.add(task)
                    dateGroup[task.dateTime.toLocalDate()] = mTasks
                }

                dateGroup[task.dateTime.toLocalDate()]?.add(task)
            }
            mToDoListState.value = mToDoListState.value.copy(
                dateGroup = dateGroup
            )

            return PageResult.Loaded(result)
        } catch (e: Exception) {
            return PageResult.Error(AppError("Failed to load task"))
        }
    }

    private fun handleDeleteTask(task: Task) {
        if (task.id != mToDoListState.value.selectedTask?.id) {
            return
        }

        viewModelScope.launch {
            mToDoListState.value = mToDoListState.value.copy(
                deleteTaskStatus = DeleteTaskStatus.Progress
            )

            val dateGroup = mToDoListState.value.dateGroup
            val dateKey = task.dateTime.toLocalDate()
            val selectedTask = dateGroup[dateKey]?.find { e -> e.id == task.id }
            if (selectedTask == null) {
                return@launch
            }

            dateGroup[dateKey]?.remove(selectedTask)
            mToDoListState.value = mToDoListState.value.copy(
                dateGroup = dateGroup
            )

            val result = taskRepository.deleteTask(task.id)
            when (result) {
                is Either.Left -> {
                    mToDoListState.value = mToDoListState.value.copy(
                        deleteTaskStatus = DeleteTaskStatus.Error,
                        selectedTask = null
                    )
                }

                is Either.Right -> {
                    mToDoListState.value = mToDoListState.value.copy(
                        deleteTaskStatus = DeleteTaskStatus.Success,
                        selectedTask = null
                    )
                }
            }
        }
    }
}