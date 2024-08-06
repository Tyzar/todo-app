package com.tyzar.test.todoapp.ui.screens.todo_list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.tyzar.test.todoapp.domain.entities.Todo
import com.tyzar.test.todoapp.ui.routeAddTodo
import com.tyzar.test.todoapp.ui.screens.todo_list.components.TodoList
import com.tyzar.test.todoapp.ui.screens.todo_list.components.TodolistTopBar
import com.tyzar.test.todoapp.ui.screens.todo_list.format.TodoItem
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

val timeZone = TimeZone.currentSystemDefault()
val now = Clock.System.now().toLocalDateTime(timeZone)
val tomorrow = now.toInstant(TimeZone.currentSystemDefault()).plus(1, DateTimeUnit.DAY, timeZone)
val todos = listOf(
    TodoItem.TodoHeader(1, now),
    TodoItem.TodoData(
        2, Todo(
            id = 1,
            title = "Todo 1",
            desc = "This is a desc on todo 23",
            dateTime = now
        )
    ),
    TodoItem.TodoData(
        3, Todo(
            id = 2,
            title = "Todo 2",
            desc = "This is a desc on todo 23",
            dateTime = now
        )
    ),
    TodoItem.TodoData(
        4, Todo(
            id = 3,
            title = "Todo 3",
            desc = "This is a desc on todo 23",
            dateTime = now
        )
    ),
    TodoItem.TodoHeader(5, now),
    TodoItem.TodoData(
        6, Todo(
            id = 4,
            title = "Todo 4",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
    TodoItem.TodoData(
        7, Todo(
            id = 5,
            title = "Todo 5",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
    TodoItem.TodoData(
        8, Todo(
            id = 6,
            title = "Todo 6",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
    TodoItem.TodoData(
        9, Todo(
            id = 7,
            title = "Todo 6",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
    TodoItem.TodoData(
        10, Todo(
            id = 8,
            title = "Todo 6",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
    TodoItem.TodoData(
        11, Todo(
            id = 9,
            title = "Todo 6",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
    TodoItem.TodoData(
        12, Todo(
            id = 10,
            title = "Todo 6",
            desc = "This is a desc on todo 23",
            dateTime = tomorrow.toLocalDateTime(timeZone)
        )
    ),
)

@Composable
fun TodoListScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TodolistTopBar(title = "To Do List", onAddTodoClick = {
            navController.navigate(routeAddTodo)
        })
    }) { padding ->
        TodoList(modifier = Modifier
            .fillMaxSize()
            .padding(padding), todos = todos, onTodoClicked = {
            Log.d("TODO", "Click...")
        }, onTodoLongClicked = {
            Log.d("TODO", "Long Click...")
        })
    }
}