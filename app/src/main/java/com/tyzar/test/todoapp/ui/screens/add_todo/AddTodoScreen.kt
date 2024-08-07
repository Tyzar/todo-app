package com.tyzar.test.todoapp.ui.screens.add_todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.tyzar.test.todoapp.ui.components.ConfirmSection
import com.tyzar.test.todoapp.ui.components.sheets.DatePickerSheet
import com.tyzar.test.todoapp.ui.components.sheets.TimePickerSheet
import com.tyzar.test.todoapp.ui.components.BaseBackTopBar
import com.tyzar.test.todoapp.ui.screens.add_todo.components.TodoForm
import com.tyzar.test.todoapp.ui.viewmodels.add_todo.AddTodoEvent
import com.tyzar.test.todoapp.ui.viewmodels.add_todo.AddTodoState
import com.tyzar.test.todoapp.ui.viewmodels.add_todo.AddTodoVM
import com.tyzar.test.todoapp.ui.viewmodels.add_todo.SaveStatus
import com.tyzar.test.todoapp.ui.viewmodels.todolist.ToDoListEvent
import com.tyzar.test.todoapp.ui.viewmodels.todolist.ToDoListVM
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(navController: NavHostController, addTodoVM: AddTodoVM, toDoListVM: ToDoListVM) {

    val formState by addTodoVM.state.collectAsStateWithLifecycle()

    val datePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val timePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(formState.saveStatus) {
        if (formState.saveStatus is SaveStatus.Error) {
            snackBarHostState.showSnackbar((formState.saveStatus as SaveStatus.Error).error.errMsg)
        } else if (formState.saveStatus == SaveStatus.Success) {
            snackBarHostState.showSnackbar("Task saved successfully")
            toDoListVM.notify(ToDoListEvent.Refresh)
            navController.popBackStack()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        BaseBackTopBar(
            title = "Add New Task",
            onBack = {
                navController.popBackStack()
            })
    }, bottomBar = {
        ConfirmSection(
            negativeText = "Cancel",
            positiveText = "Save",
            enabledNegative = formState.saveStatus != SaveStatus.Loading,
            enabledPositive = formState.saveStatus != SaveStatus.Loading
                    || formState.invalidFields.isNotEmpty(),
            onNegative = {
                navController.popBackStack()
            },
            onPositive = {
                addTodoVM.notify(AddTodoEvent.Save)
            })
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }) { padding ->
        TodoForm(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            onRequestShowDatePicker = {
                showDatePicker = true
            },
            formData = formState,
            onToggleUseTime = {
                addTodoVM.notify(AddTodoEvent.ToggleUseTime(it))
            },
            onRequestShowTimePicker = {
                showTimePicker = true
            },
            onFieldChanged = { label, text ->
                when (label) {
                    AddTodoState.TITLE -> addTodoVM.notify(AddTodoEvent.UpdateTitle(text))
                    AddTodoState.DESC -> addTodoVM.notify(AddTodoEvent.UpdateDesc(text))
                }
            }
        )
    }

    if (showDatePicker) {
        DatePickerSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(620.dp),
            onDismissRequest = { showDatePicker = false },
            sheetState = datePickerSheetState,
            initialDate = if (formState.date == null) LocalDate.now() else formState.date,
            onCancel = { showDatePicker = false },
            onSave = { date ->
                if (date != null) {
                    addTodoVM.notify(AddTodoEvent.UpdateDate(date))
                }
                showDatePicker = false
            })
    }

    if (showTimePicker) {
        TimePickerSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(650.dp),
            sheetState = timePickerSheetState,
            initialTime = if (formState.time == null) LocalTime.now() else formState.time!!,
            onCancel = { showTimePicker = false }, onSave = {
                showTimePicker = false
                addTodoVM.notify(AddTodoEvent.UpdateTime(it))
            })
    }
}