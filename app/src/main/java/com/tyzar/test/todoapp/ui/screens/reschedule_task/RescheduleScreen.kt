package com.tyzar.test.todoapp.ui.screens.reschedule_task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.tyzar.test.todoapp.core.formatter.displayOnlyDateFormat
import com.tyzar.test.todoapp.core.formatter.displayOnlyTimeFormat
import com.tyzar.test.todoapp.core.formatter.formatDate
import com.tyzar.test.todoapp.ui.components.AppTextField
import com.tyzar.test.todoapp.ui.components.BaseBackTopBar
import com.tyzar.test.todoapp.ui.components.ConfirmSection
import com.tyzar.test.todoapp.ui.components.sheets.DatePickerSheet
import com.tyzar.test.todoapp.ui.components.sheets.TimePickerSheet
import com.tyzar.test.todoapp.ui.viewmodels.reschedule_task.RescheduleTaskEvent
import com.tyzar.test.todoapp.ui.viewmodels.reschedule_task.RescheduleTaskStatus
import com.tyzar.test.todoapp.ui.viewmodels.reschedule_task.RescheduleTaskVM
import com.tyzar.test.todoapp.ui.viewmodels.todolist.ToDoListEvent
import com.tyzar.test.todoapp.ui.viewmodels.todolist.ToDoListVM
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RescheduleScreen(
    navHostController: NavHostController,
    rescheduleTaskVM: RescheduleTaskVM,
    taskId: String,
    toDoListVM: ToDoListVM
) {
    val state by rescheduleTaskVM.state.collectAsStateWithLifecycle()

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

    LaunchedEffect(state.rescheduleStatus) {
        when (state.rescheduleStatus) {
            RescheduleTaskStatus.Uninitiated -> {
                rescheduleTaskVM.notify(RescheduleTaskEvent.LoadTask(taskId))
            }

            RescheduleTaskStatus.Error -> {
                snackBarHostState.showSnackbar("Failed to reschedule task")
            }

            RescheduleTaskStatus.Success -> {
                toDoListVM.notify(ToDoListEvent.Refresh)
                navHostController.popBackStack()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, topBar = {
        BaseBackTopBar(title = "Reschedule", onBack = {
            toDoListVM.notify(ToDoListEvent.SelectTask(null))
            navHostController.popBackStack()
        })
    }, bottomBar = {
        ConfirmSection(
            negativeText = "Cancel",
            positiveText = "Save",
            onNegative = { navHostController.popBackStack() }, onPositive = {
                rescheduleTaskVM.notify(RescheduleTaskEvent.Reschedule)
            })
    }) { padding ->
        when (state.task) {
            null -> Box(modifier = Modifier.size(0.dp))
            else -> {
                val task = state.task!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    Text(text = "Date", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    AppTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = "Select Date",
                        readOnly = true,
                        singleLine = true,
                        value = formatDate(
                            task.dateTime,
                            displayOnlyDateFormat
                        ),
                        onClick = {
                            showDatePicker = true
                        },
                        onValueChanged = {

                        })
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Time", style = MaterialTheme.typography.titleMedium)
                    AppTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = "Set Time",
                        readOnly = true,
                        singleLine = true,
                        value = formatDate(
                            task.dateTime,
                            displayOnlyTimeFormat
                        ),
                        onClick = {
                            showTimePicker = true
                        },
                        onValueChanged = {})
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(620.dp),
            onDismissRequest = { showDatePicker = false },
            sheetState = datePickerSheetState,
            initialDate = if (state.task?.dateTime == null) LocalDate.now() else state.task?.dateTime?.toLocalDate(),
            onCancel = { showDatePicker = false },
            onSave = { date ->
                if (date != null) {
                    rescheduleTaskVM.notify(RescheduleTaskEvent.UpdateDate(date))
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
            initialTime = if (state.task?.dateTime == null) LocalTime.now() else state.task?.dateTime?.toLocalTime()!!,
            onCancel = { showTimePicker = false }, onSave = {
                showTimePicker = false
                rescheduleTaskVM.notify(RescheduleTaskEvent.UpdateTime(it))
            })
    }
}