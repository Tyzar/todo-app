package com.tyzar.test.todoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmSection(
    modifier: Modifier = Modifier,
    negativeText: String,
    positiveText: String,
    enabledPositive: Boolean = true,
    enabledNegative: Boolean = true,
    onNegative: () -> Unit,
    onPositive: () -> Unit
) {
    Surface(modifier.background(color = MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppOutlinedButton(
                modifier = Modifier.weight(1f, true),
                label = negativeText,
                shape = MaterialTheme.shapes.small,
                enabled = enabledNegative,
                onClick = onNegative
            )
            Spacer(modifier = Modifier.width(16.dp))
            AppContainedButton(
                modifier = Modifier.weight(1f, true),
                label = positiveText,
                enabled = enabledPositive,
                shape = MaterialTheme.shapes.small,
                onClick = onPositive
            )
        }
    }
}