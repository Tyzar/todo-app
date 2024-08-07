package com.tyzar.test.todoapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tyzar.test.todoapp.ui.screens.add_todo.AddTodoScreen
import com.tyzar.test.todoapp.ui.screens.reschedule_task.RescheduleScreen
import com.tyzar.test.todoapp.ui.screens.todo_list.TodoListScreen

const val routeTodoList = "/todo-list"
const val routeAddTodo = "/add-todo"
const val routeReschedule = "/reschedule"

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = routeTodoList, route = "/") {
        composable(route = routeTodoList) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("/")
            }

            TodoListScreen(navController, hiltViewModel(parentEntry))
        }

        composable(route = routeAddTodo) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("/")
            }

            AddTodoScreen(navController, hiltViewModel(), hiltViewModel(parentEntry))
        }

        composable(route = "$routeReschedule/{taskId}", arguments = listOf(navArgument("taskId") {
            type = NavType.StringType
        })) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("/")
            }

            RescheduleScreen(
                navHostController = navController,
                rescheduleTaskVM = hiltViewModel(),
                taskId = taskId,
                toDoListVM = hiltViewModel(parentEntry)
            )
        }
    }
}