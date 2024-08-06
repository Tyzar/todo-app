package com.tyzar.test.todoapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tyzar.test.todoapp.ui.screens.add_todo.AddTodoScreen
import com.tyzar.test.todoapp.ui.screens.todo_list.TodoListScreen

const val routeTodoList = "/todo-list"
const val routeAddTodo = "/add-todo"

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = routeTodoList) {
        composable(route = routeTodoList) {
            TodoListScreen(navController)
        }

        composable(route = routeAddTodo) {
            AddTodoScreen(navController)
        }
    }
}