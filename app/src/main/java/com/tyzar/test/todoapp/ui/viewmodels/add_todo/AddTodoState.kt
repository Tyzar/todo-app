package com.tyzar.test.todoapp.ui.viewmodels.add_todo

import com.tyzar.test.todoapp.core.errors.AppError
import java.time.LocalDate
import java.time.LocalTime

sealed class SaveStatus {
    data object Unsaved : SaveStatus()
    data object Loading : SaveStatus()
    data class Error(val error: AppError) : SaveStatus()
    data object Success : SaveStatus()
}

data class AddTodoState(
    val title: String = "",
    val desc: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val useTime: Boolean = false,
    val invalidFields: Map<String, String> = emptyMap(),
    val saveStatus: SaveStatus = SaveStatus.Unsaved
) {
    companion object {
        const val TITLE = "title"
        const val DESC = "desc"
        const val DATE = "date"
    }
}