package com.example.quanlythuvien.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0066CC),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCEEFF),
    onPrimaryContainer = Color(0xFF001E3A),
    secondary = Color(0xFF5C5F72),
    onSecondary = Color.White,
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF1A1C1E),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE0E2EC),
    onSurfaceVariant = Color(0xFF44474F),
    outline = Color(0xFF74777F),
    outlineVariant = Color(0xFFC4C6CF),
    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun QuanLyThuVienTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
