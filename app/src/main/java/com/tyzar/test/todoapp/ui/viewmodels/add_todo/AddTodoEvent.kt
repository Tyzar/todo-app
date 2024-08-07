package com.tyzar.test.todoapp.ui.viewmodels.add_todo

import java.time.LocalDate
import java.time.LocalTime

sealed class AddTodoEvent {
    data class UpdateTitle(val textValue: String) : AddTodoEvent()
    data class UpdateDesc(val textValue: String) : AddTodoEvent()
    data class UpdateDate(val date: LocalDate) : AddTodoEvent()
    data class UpdateTime(val time: LocalTime) : AddTodoEvent()
    data class ToggleUseTime(val enabled: Boolean) : AddTodoEvent()
    data object Save : AddTodoEvent()
}
