package com.tyzar.test.todoapp.ui.viewmodels.reschedule_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RescheduleTaskVM @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private val mState = MutableStateFlow(RescheduleTaskState())
    val state = mState.asStateFlow()

    fun notify(event: RescheduleTaskEvent) {
        when (event) {
            is RescheduleTaskEvent.LoadTask -> handleLoadTask(event.taskId)
            is RescheduleTaskEvent.Reschedule -> handleReschedule()
            is RescheduleTaskEvent.UpdateDate -> {
                if (mState.value.task == null) {
                    return
                }

                val task = mState.value.task
                val updTask = task!!.copy(
                    dateTime = LocalDateTime.of(
                        event.localDate,
                        task.dateTime.toLocalTime()
                    )
                )
                mState.value = mState.value.copy(
                    task = updTask
                )
            }

            is RescheduleTaskEvent.UpdateTime -> {
                if (mState.value.task == null) {
                    return
                }

                val task = mState.value.task
                val updTask = task!!.copy(
                    dateTime = LocalDateTime.of(
                        task.dateTime.toLocalDate(),
                        event.localTime
                    )
                )
                mState.value = mState.value.copy(
                    task = updTask
                )
            }
        }
    }

    private fun handleReschedule() {
        viewModelScope.launch {
            if (mState.value.task == null) {
                return@launch
            }

            val result =
                taskRepository.rescheduleTask(mState.value.task!!.id, mState.value.task!!.dateTime)
            when (result) {
                is Either.Left -> mState.value = mState.value.copy(
                    rescheduleStatus = RescheduleTaskStatus.Error
                )

                is Either.Right -> mState.value = mState.value.copy(
                    rescheduleStatus = RescheduleTaskStatus.Success
                )
            }
        }
    }

    private fun handleLoadTask(taskId: String) {
        viewModelScope.launch {
            val task = taskRepository.getTask(taskId)
            mState.value = mState.value.copy(
                task = task
            )
        }
    }
}