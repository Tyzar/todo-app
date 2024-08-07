package com.tyzar.test.todoapp.ui.screens.add_todo.components

import androidx.compose.foundation.Image
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
import com.tyzar.test.todoapp.core.formatter.displayOnlyDateFormat
import com.tyzar.test.todoapp.core.formatter.displayOnlyTimeFormat
import com.tyzar.test.todoapp.core.formatter.formatDate
import com.tyzar.test.todoapp.core.formatter.formatTime
import com.tyzar.test.todoapp.ui.components.AppTextField
import com.tyzar.test.todoapp.ui.viewmodels.add_todo.AddTodoState
import com.tyzar.test.todoapp.ui.viewmodels.add_todo.SaveStatus

typealias OnRequestShowDatePicker = () -> Unit
typealias OnRequestShowTimePicker = () -> Unit
typealias OnToggleUseTime = (Boolean) -> Unit
typealias OnFieldChanged = (String, String) -> Unit

@Composable
fun TodoForm(
    modifier: Modifier = Modifier,
    formData: AddTodoState,
    onRequestShowDatePicker: OnRequestShowDatePicker,
    onRequestShowTimePicker: OnRequestShowTimePicker,
    onToggleUseTime: OnToggleUseTime,
    onFieldChanged: OnFieldChanged
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
            enabled = formData.saveStatus != SaveStatus.Loading,
            singleLine = true,
            value = formData.title,
            onValueChanged = {
                onFieldChanged(AddTodoState.TITLE, it)
            })
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Description", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 4,
            enabled = formData.saveStatus != SaveStatus.Loading,
            value = formData.desc,
            placeholder = "Description Task",
            onValueChanged = {
                onFieldChanged(AddTodoState.DESC, it)
            })
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Date", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = "Select Date",
            readOnly = true,
            enabled = formData.saveStatus != SaveStatus.Loading,
            singleLine = true,
            value = if (formData.date == null) "" else formatDate(
                formData.date,
                displayOnlyDateFormat
            ),
            onClick = {
                onRequestShowDatePicker()
            },
            onValueChanged = {

            })
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
            Switch(checked = formData.useTime, onCheckedChange = onToggleUseTime)
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (formData.useTime) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = "Set Time",
                readOnly = true,
                enabled = formData.saveStatus != SaveStatus.Loading,
                singleLine = true,
                value = if (formData.time == null) "" else formatTime(
                    formData.time,
                    displayOnlyTimeFormat
                ),
                onClick = {
                    onRequestShowTimePicker()
                },
                onValueChanged = {})
        }
        Spacer(modifier = Modifier.height(if (formData.useTime) 20.dp else 8.dp))
    }
}