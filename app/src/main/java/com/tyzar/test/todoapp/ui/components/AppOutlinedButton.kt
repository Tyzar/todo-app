package com.tyzar.test.todoapp.ui.components

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    label: String,
    shape: Shape,
    onClick: () -> Unit
) {
    OutlinedButton(modifier = modifier, shape = shape, onClick = onClick) {
        Text(text = label)
    }
}