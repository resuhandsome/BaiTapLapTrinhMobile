package com.example.thuchanh2day4.ui.theme // Đảm bảo đúng package nếu bạn tạo file này

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
// import androidx.compose.foundation.isSystemInDarkTheme // Nếu muốn hỗ trợ Dark Theme
// import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Định nghĩa bảng màu sáng
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007AFF), // Màu xanh dương cho các thành phần chính (nút, indicator)
    onPrimary = Color.White,     // Màu chữ/icon trên nền primary
    primaryContainer = Color(0xFFD1E6FA), // Màu nền cho container liên quan đến primary
    onPrimaryContainer = Color(0xFF001E3A), // Màu chữ/icon trên nền primaryContainer
    background = Color.White,    // Màu nền chung của ứng dụng
    onBackground = Color.Black,  // Màu chữ/icon trên nền background
    surface = Color.White,       // Màu nền cho các bề mặt như Card, Sheet
    onSurface = Color.Black,     // Màu chữ/icon trên nền surface
    onSurfaceVariant = Color.DarkGray // Màu cho các biến thể text (ví dụ: mô tả)
    // Bạn có thể định nghĩa thêm các màu khác như secondary, tertiary, error, v.v.
)

/*
// Bỏ comment nếu bạn muốn hỗ trợ Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3895FF),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF004A7F),
    onPrimaryContainer = Color(0xFFD1E6FA),
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray
    // ...
)
*/

@Composable
fun Thuchanh2day4Theme( // Tên Theme của bạn
    // darkTheme: Boolean = isSystemInDarkTheme(), // Bỏ comment để tự động chuyển Dark Theme
    content: @Composable () -> Unit
) {
    // val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme // Logic chọn theme
    val colorScheme = LightColorScheme // Hiện tại chỉ dùng Light Theme

    MaterialTheme(
        colorScheme = colorScheme,
        // typography = Typography, // Tham chiếu đến file Typography.kt nếu có
        // shapes = Shapes,       // Tham chiếu đến file Shapes.kt nếu có
        content = content
    )
}
