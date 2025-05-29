package com.example.navigation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Components List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Nhóm Display
            item { SectionHeader("Display") }
            item {
                MenuListItem(title = "Text", description = "Displays text") {
                    navController.navigate(Screen.TextDetail.route)
                }
            }
            item {
                MenuListItem(title = "Image", description = "Displays an image") {
                    navController.navigate(Screen.ImageDetail.route)
                }
            }

            // Nhóm Input
            item { SectionHeader("Input") }
            item {
                MenuListItem(title = "TextField", description = "Input field for text") {
                    navController.navigate(Screen.TextFieldDetail.route)
                }
            }
            item {
                MenuListItem(title = "PasswordField", description = "Input field for passwords") {
                    navController.navigate(Screen.PasswordFieldDetail.route)
                }
            }

            // Nhóm Layout
            item { SectionHeader("Layout") }
            item {
                MenuListItem(title = "Colum", description = "Arranges elements vertically") {
                    navController.navigate(Screen.ColumnDetail.route)
                }
            }
            item {
                MenuListItem(title = "Row", description = "Arranges elements horizontally") {
                    navController.navigate(Screen.RowDetail.route)
                }
            }
            item {
                MenuListItem(title = "Box", description = "Stacks or aligns elements") {
                    navController.navigate(Screen.BoxDetail.route)
                }
            }

            }
        }
    }


@Composable
fun MenuListItem(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = description, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

