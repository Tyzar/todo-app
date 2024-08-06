package com.tyzar.test.todoapp.ui.screens.add_todo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tyzar.test.todoapp.R
import com.tyzar.test.todoapp.domain.entities.Todo
import com.tyzar.test.todoapp.ui.components.AppTextField

typealias OnRequestShowDatePicker = () -> Unit
typealias OnRequestShowTimePicker = () -> Unit

@Composable
fun TodoForm(
    modifier: Modifier = Modifier,
    formData: Todo? = null,
    onRequestShowDatePicker: OnRequestShowDatePicker,
    onRequestShowTimePicker: OnRequestShowTimePicker
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Title", style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Title Task",
            singleLine = true,
            value = "",
            onValueChanged = {})
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Description", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 4,
            value = "",
            placeholder = "Description Task",
            onValueChanged = { })
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Date", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = "Select Date",
            readOnly = true,
            singleLine = true,
            value = "",
            onClick = {
                onRequestShowDatePicker()
            },
            onValueChanged = {})
        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(color = Color.Gray),
                painter = painterResource(id = R.drawable.ic_timer_outlined),
                contentDescription = "timer-icon"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Time", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f, true))
            Switch(checked = false, onCheckedChange = {})
        }
    }
}