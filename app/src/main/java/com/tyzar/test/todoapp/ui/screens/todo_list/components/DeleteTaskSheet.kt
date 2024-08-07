package com.tyzar.test.todoapp.ui.screens.todo_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tyzar.test.todoapp.domain.entities.Task
import com.tyzar.test.todoapp.ui.components.AppContainedButton
import com.tyzar.test.todoapp.ui.components.AppOutlinedButton
import com.tyzar.test.todoapp.ui.components.sheets.AppModalSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTaskSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    task: Task,
    onCancel: () -> Unit,
    onConfirm: (task: Task) -> Unit
) {
    AppModalSheet(
        modifier = modifier,
        sheetState = sheetState,
        useTitleSeparator = false,
        title = "Delete",
        onDismissRequest = onCancel
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Are you sure to delete", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppOutlinedButton(
                    modifier = Modifier.weight(1f, true),
                    label = "Cancel",
                    shape = MaterialTheme.shapes.small,
                    onClick = onCancel
                )
                Spacer(modifier = Modifier.width(16.dp))
                AppContainedButton(
                    modifier = Modifier.weight(1f, true),
                    label = "Delete",
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        onConfirm(task)
                    })
            }
        }
    }
}