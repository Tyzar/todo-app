package com.tyzar.test.todoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val lightColorScheme =
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        error = errorLight,
        onError = onErrorLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        scrim = scrimLight
    )

val darkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    error = errorDark,
    onError = onErrorDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    outline = outlineDark,
    scrim = scrimDark
)

@Composable
fun TodoAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val selectedColorScheme =
        if (useDarkTheme) darkColorScheme
        else lightColorScheme
    MaterialTheme(
        colorScheme = selectedColorScheme,
        content = content,
        typography = typography,
        shapes = shapes
    )
}