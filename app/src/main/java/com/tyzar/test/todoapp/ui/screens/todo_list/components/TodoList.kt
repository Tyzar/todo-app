package com.tyzar.test.todoapp.ui.screens.todo_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tyzar.test.todoapp.domain.entities.Todo
import com.tyzar.test.todoapp.ui.screens.todo_list.format.TodoItem

typealias OnTodoClicked = (todo: Todo) -> Unit
typealias OnTodoLongClicked = (todo: Todo) -> Unit

@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    todos: List<TodoItem>,
    onTodoClicked: OnTodoClicked,
    onTodoLongClicked: OnTodoLongClicked
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = todos.size, key = { todos[it].key }, contentType = {
            todos[it] is TodoItem.TodoData
        }) {
            when (val todoItem = todos[it]) {
                is TodoItem.TodoData -> TodoListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    todoItem = todoItem,
                    onClicked = {
                        onTodoClicked(todoItem.todo)
                    },
                    onLongClicked = {
                        onTodoLongClicked(todoItem.todo)
                    }
                )

                is TodoItem.TodoHeader -> TodoListHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    todoItem = todoItem
                )
            }
        }
    }
}

@Composable
fun TodoListHeader(modifier: Modifier = Modifier, todoItem: TodoItem.TodoHeader) {
    Text(modifier = modifier, text = todoItem.dateTime.toString())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListItem(
    modifier: Modifier = Modifier,
    todoItem: TodoItem.TodoData,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit
) {
    val haptics = LocalHapticFeedback.current
    Card(modifier = modifier.combinedClickable(
        onClick = onClicked,
        onLongClick = {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            onLongClicked()
        }
    )) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(modifier = Modifier.size(24.dp), checked = false, onCheckedChange = {})
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(fill = true, weight = 1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = todoItem.todo.title,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = todoItem.todo.dateTime.toString())
            }
        }
    }
}