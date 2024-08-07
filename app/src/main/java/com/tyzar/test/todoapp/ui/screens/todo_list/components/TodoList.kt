package com.tyzar.test.todoapp.ui.screens.todo_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nokotogi.mantra.compose.paging.column.MtrColumnPagin
import com.nokotogi.mantra.compose.paging.states.MtrPageState
import com.nokotogi.mantra.compose.paging.states.PageResult
import com.tyzar.test.todoapp.core.errors.AppError
import com.tyzar.test.todoapp.core.formatter.displayOnlyDateFormat
import com.tyzar.test.todoapp.core.formatter.displayOnlyTimeFormat
import com.tyzar.test.todoapp.core.formatter.formatDate
import com.tyzar.test.todoapp.domain.entities.Task
import com.tyzar.test.todoapp.ui.components.AppContainedButton
import java.time.LocalDate

typealias OnRequestComplete = (task: Task) -> Unit
typealias OnTodoLongClicked = (task: Task) -> Unit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    todos: Map<LocalDate, List<Task>>,
    pagingState: MtrPageState<AppError, Task>,
    onRequestComplete: OnRequestComplete,
    onTodoLongClicked: OnTodoLongClicked,
    onRequestLoadPage: () -> Unit,
) {
    MtrColumnPagin(
        modifier = modifier,
        onRequestLoadNewPage = onRequestLoadPage
    ) {
        todos.entries.forEach { dateGroup ->
            stickyHeader {
                TodoListHeader(
                    modifier = Modifier
                        .fillMaxWidth(),
                    date = dateGroup.key
                )
            }

            items(count = dateGroup.value.size, key = { dateGroup.value[it].id }) {
                TodoListItem(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp),
                    task = dateGroup.value[it],
                    onChecked = {
                        onRequestComplete(dateGroup.value[it])
                    },
                    onLongClicked = { onTodoLongClicked(dateGroup.value[it]) }
                )
            }
        }

        if (pagingState.pageResult is PageResult.Loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(60.dp))
                }
            }
        } else if (pagingState.pageResult is PageResult.Error) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = pagingState.pageResult.error?.errMsg ?: "An error occurred")
                    Spacer(modifier = Modifier.height(16.dp))
                    AppContainedButton(
                        label = "Reload",
                        shape = MaterialTheme.shapes.small,
                        onClick = onRequestLoadPage
                    )
                }
            }
        }
    }
}

@Composable
fun TodoListHeader(modifier: Modifier = Modifier, date: LocalDate) {
    Surface(modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = formatDate(date, displayOnlyDateFormat)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListItem(
    modifier: Modifier = Modifier,
    task: Task,
    onChecked: () -> Unit,
    onLongClicked: () -> Unit
) {
    val haptics = LocalHapticFeedback.current
    Card(modifier = modifier
        .padding(vertical = 8.dp)
        .combinedClickable(
            onClick = {},
            onLongClick = {
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                onLongClicked()
            }
        )) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(modifier = Modifier.size(24.dp), checked = task.isDone, onCheckedChange = {
                if (it && !task.isDone) {
                    onChecked()
                }
            })
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(fill = true, weight = 1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = task.title,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = formatDate(task.dateTime, displayOnlyTimeFormat))
            }
        }
    }
}