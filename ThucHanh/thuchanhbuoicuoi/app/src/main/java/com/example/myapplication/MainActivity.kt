package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                BackgroundChangeScreen()
            }
        }
    }
}

@Composable
fun BackgroundChangeScreen() {
    val context = LocalContext.current
    val repository = remember { BackgroundPreferencesRepository(context) }
    val viewModel: BackgroundViewModel = viewModel { BackgroundViewModel(repository) }

    val backgroundColor by viewModel.backgroundColor.collectAsState()
    val selectedColor by viewModel.selectedColor.collectAsState()

    val currentColor = when (backgroundColor) {
        BackgroundPreferencesRepository.WHITE -> Color.White
        BackgroundPreferencesRepository.BLACK -> Color.Black
        BackgroundPreferencesRepository.PINK -> Color(0xFFFF69B4)  // Màu hồng
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(currentColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Chọn màu nền",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Radio buttons cho việc chọn màu
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ColorOption(
                        color = BackgroundPreferencesRepository.WHITE,
                        colorName = "Trắng",
                        displayColor = Color.White,
                        isSelected = selectedColor == BackgroundPreferencesRepository.WHITE,
                        onSelect = { viewModel.selectColor(BackgroundPreferencesRepository.WHITE) }
                    )

                    ColorOption(
                        color = BackgroundPreferencesRepository.BLACK,
                        colorName = "Đen",
                        displayColor = Color.Black,
                        isSelected = selectedColor == BackgroundPreferencesRepository.BLACK,
                        onSelect = { viewModel.selectColor(BackgroundPreferencesRepository.BLACK) }
                    )

                    ColorOption(
                        color = BackgroundPreferencesRepository.PINK,
                        colorName = "Hồng",
                        displayColor = Color(0xFFFF69B4),
                        isSelected = selectedColor == BackgroundPreferencesRepository.PINK,
                        onSelect = { viewModel.selectColor(BackgroundPreferencesRepository.PINK) }
                    )
                }

                // Nút Save
                Button(
                    onClick = { viewModel.saveBackgroundColor() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "save",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Hiển thị màu hiện tại
                Text(
                    text = "Màu hiện tại: ${getColorName(backgroundColor)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ColorOption(
    color: String,
    colorName: String,
    displayColor: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onSelect
            )
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF4CAF50)
            )
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = displayColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(start = 8.dp)
        )

        Text(
            text = colorName,
            modifier = Modifier.padding(start = 16.dp),
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun getColorName(color: String): String {
    return when (color) {
        BackgroundPreferencesRepository.WHITE -> "Trắng"
        BackgroundPreferencesRepository.BLACK -> "Đen"
        BackgroundPreferencesRepository.PINK -> "Hồng"
        else -> "Không xác định"
    }
}
