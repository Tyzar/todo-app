package com.tyzar.test.todoapp.ui.screens.add_todo

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.tyzar.test.todoapp.ui.components.ConfirmSection
import com.tyzar.test.todoapp.ui.components.sheets.DatePickerSheet
import com.tyzar.test.todoapp.ui.screens.add_todo.components.AddTodoTopBar
import com.tyzar.test.todoapp.ui.screens.add_todo.components.TodoForm
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(navController: NavHostController) {

    val datePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AddTodoTopBar(
            title = "Add New Task",
            onBack = {
                navController.popBackStack()
            })
    }, bottomBar = {
        ConfirmSection(
            negativeText = "Cancel",
            positiveText = "Save",
            onNegative = { },
            onPositive = { })
    }) { padding ->
        TodoForm(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            onRequestShowDatePicker = {
                showDatePicker = true
            },
            onRequestShowTimePicker = {}
        )
    }

    if (showDatePicker) {
        DatePickerSheet(
            onDismissRequest = { showDatePicker = false },
            sheetState = datePickerSheetState,
            initialDate = LocalDate.now(),
            onCancel = { showDatePicker = false },
            onSave = { date ->
                Log.d("TODO", date.toString())
                showDatePicker = false
            })
    }
}