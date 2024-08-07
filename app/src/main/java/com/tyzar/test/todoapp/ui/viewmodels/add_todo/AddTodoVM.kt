package com.tyzar.test.todoapp.ui.viewmodels.add_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokotogi.mantra.either.Either
import com.tyzar.test.todoapp.domain.entities.Task
import com.tyzar.test.todoapp.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTodoVM @Inject constructor(private val taskRepo: TaskRepository) : ViewModel() {
    private val mState = MutableStateFlow(AddTodoState())
    val state = mState.asStateFlow()

    fun notify(event: AddTodoEvent) {
        when (event) {
            is AddTodoEvent.UpdateDate -> {
                mState.value = mState.value.copy(
                    date = event.date
                )
                isFormValid()
            }

            is AddTodoEvent.UpdateDesc -> {
                mState.value = mState.value.copy(
                    desc = event.textValue
                )
                isFormValid()
            }

            is AddTodoEvent.UpdateTime -> {
                mState.value = mState.value.copy(
                    time = event.time
                )
            }

            is AddTodoEvent.UpdateTitle -> {
                mState.value = mState.value.copy(
                    title = event.textValue
                )
                isFormValid()
            }

            is AddTodoEvent.ToggleUseTime -> {
                mState.value = mState.value.copy(
                    useTime = event.enabled
                )
            }

            AddTodoEvent.Save -> handleSave()
        }
    }

    private fun handleSave() {
        viewModelScope.launch {
            if (!isFormValid()) {
                return@launch
            }

            mState.value = mState.value.copy(
                saveStatus = SaveStatus.Loading
            )

            val time =
                if (mState.value.useTime && mState.value.time != null)
                    mState.value.time
                else LocalTime.of(
                    0,
                    0
                )

            val task = Task(
                id = UUID.randomUUID().toString(),
                title = mState.value.title,
                desc = mState.value.desc,
                dateTime = LocalDateTime.of(mState.value.date, time),
            )
            when (val saveResult = taskRepo.addTask(task)) {
                is Either.Left -> {
                    val error = saveResult.leftValue
                    mState.value = mState.value.copy(
                        saveStatus = SaveStatus.Error(error)
                    )
                }

                is Either.Right -> {
                    mState.value = mState.value.copy(
                        saveStatus = SaveStatus.Success
                    )
                }
            }
        }
    }

    private fun isFormValid(): Boolean {
        val invalidFields = getInvalidFields()
        mState.value = mState.value.copy(
            invalidFields = invalidFields
        )

        return invalidFields.isEmpty()
    }

    private fun getInvalidFields(): Map<String, String> {
        val formState = mState.value
        val invalids = mutableMapOf<String, String>()

        if (formState.title.isEmpty()) {
            invalids[AddTodoState.TITLE] = "Title cannot be empty"
        }

        if (formState.desc.isEmpty()) {
            invalids[AddTodoState.DESC] = "Description cannot be empty"
        }

        if (formState.date == null) {
            invalids[AddTodoState.DATE] = "Date must be set"
        }

        return invalids
    }
}