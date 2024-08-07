package com.tyzar.test.todoapp.ui.screens.todo_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskActionBar(
    modifier: Modifier = Modifier,
    onCloseAction: () -> Unit,
    onReschedule: () -> Unit,
    onRequestDelete: () -> Unit
) {
    Surface(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onCloseAction() },
                imageVector = Icons.Outlined.Close,
                contentDescription = "cancel"
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.clickable { onReschedule() },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "reschedule"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Reschedule", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.clickable { onRequestDelete() },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.error,
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Delete", style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }

        }
    }
}