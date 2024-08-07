package com.tyzar.test.todoapp.ui.components

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun AppContainedButton(
    modifier: Modifier = Modifier,
    label: String,
    shape: Shape,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ElevatedButton(modifier = modifier, enabled = enabled, shape = shape, onClick = onClick) {
        Text(text = label)
    }
}