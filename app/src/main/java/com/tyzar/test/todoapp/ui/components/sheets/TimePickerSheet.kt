package com.tyzar.test.todoapp.ui.components.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tyzar.test.todoapp.ui.components.ConfirmSection
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    initialTime: LocalTime,
    onCancel: () -> Unit,
    onSave: (time: LocalTime) -> Unit
) {
    AppModalSheet(
        modifier = modifier,
        sheetState = sheetState,
        title = "Set Time",
        onDismissRequest = onCancel
    ) {
        val timePickerState = rememberTimePickerState(
            initialHour = initialTime.hour,
            initialMinute = initialTime.minute,
            is24Hour = true
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                state = timePickerState,
                layoutType = TimePickerLayoutType.Vertical
            )
            Spacer(modifier = Modifier.height(30.dp))
            ConfirmSection(
                negativeText = "Cancel",
                positiveText = "Save",
                onNegative = onCancel, onPositive = {
                    onSave(LocalTime.of(timePickerState.hour, timePickerState.minute))
                })
        }
    }
}