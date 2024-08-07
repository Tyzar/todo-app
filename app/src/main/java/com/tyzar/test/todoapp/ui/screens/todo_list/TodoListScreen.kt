package com.tyzar.test.todoapp.ui.screens.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.nokotogi.mantra.compose.paging.states.PageResult
import com.tyzar.test.todoapp.ui.routeAddTodo
import com.tyzar.test.todoapp.ui.routeReschedule
import com.tyzar.test.todoapp.ui.screens.todo_list.components.DeleteTaskSheet
import com.tyzar.test.todoapp.ui.screens.todo_list.components.TaskActionBar
import com.tyzar.test.todoapp.ui.screens.todo_list.components.TodoList
import com.tyzar.test.todoapp.ui.screens.todo_list.components.TodolistTopBar
import com.tyzar.test.todoapp.ui.viewmodels.todolist.DeleteTaskStatus
import com.tyzar.test.todoapp.ui.viewmodels.todolist.ToDoListEvent
import com.tyzar.test.todoapp.ui.viewmodels.todolist.ToDoListVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavHostController, toDoListVM: ToDoListVM) {
    val toDoListState by toDoListVM.toDoListState.collectAsStateWithLifecycle()

    var showTaskAction by remember {
        mutableStateOf(false)
    }

    var showDeleteTaskSheet by remember {
        mutableStateOf(false)
    }
    val deleteTaskSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(toDoListState.deleteTaskStatus) {
        if (toDoListState.deleteTaskStatus == DeleteTaskStatus.Error) {
            snackBarHostState.showSnackbar("Failed to delete task")
        } else if (toDoListState.deleteTaskStatus == DeleteTaskStatus.Success) {
            snackBarHostState.showSnackbar("Success delete task")
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            if (!showTaskAction) TodolistTopBar(
                modifier = Modifier.background(color = Color.White),
                title = "To Do List",
                onAddTodoClick = {
                    navController.navigate(routeAddTodo)
                }) else TaskActionBar(
                onCloseAction = {
                    showTaskAction = false
                    toDoListVM.notify(ToDoListEvent.SelectTask(null))
                },
                onReschedule = {
                    showTaskAction = false
                    navController.navigate("$routeReschedule/${toDoListState.selectedTask?.id}")
                },
                onRequestDelete = {
                    showTaskAction = false
                    showDeleteTaskSheet = true
                })
        }) { padding ->
        TodoList(modifier = Modifier
            .fillMaxSize()
            .padding(padding), todos = toDoListState.dateGroup,
            selectedTask = toDoListState.selectedTask,
            onRequestLoadPage = {
                toDoListVM.notify(ToDoListEvent.Load)
            },
            onRequestComplete = {
                toDoListVM.notify(ToDoListEvent.CompleteTask(it))
            }, onTodoLongClicked = {
                toDoListVM.notify(ToDoListEvent.SelectTask(it))
                showTaskAction = true
            })
    }

    if (showDeleteTaskSheet && toDoListState.selectedTask != null) {
        DeleteTaskSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            sheetState = deleteTaskSheetState,
            task = toDoListState.selectedTask!!,
            onCancel = {
                showDeleteTaskSheet = false
                toDoListVM.notify(ToDoListEvent.SelectTask(null))
            }, onConfirm = {
                showDeleteTaskSheet = false
                toDoListVM.notify(ToDoListEvent.DeleteTask(it))
            })
    }

    if (toDoListState.deleteTaskStatus == DeleteTaskStatus.Progress) {
        Dialog(onDismissRequest = { }) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(20.dp)
                )
            }
        }
    }
}