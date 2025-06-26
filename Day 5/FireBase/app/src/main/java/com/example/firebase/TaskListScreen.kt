package com.example.firebase

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    taskViewModel: TaskViewModel
) {
    val uiState by taskViewModel.taskListUiState.collectAsStateWithLifecycle()
    val crudUiState by taskViewModel.crudUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(crudUiState) {
        crudUiState.successMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            taskViewModel.resetCrudState()
        }
        crudUiState.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            taskViewModel.resetCrudState()
        }
    }

    val colors = listOf(Color(0xFFE0F7FA), Color(0xFFFFF9C4), Color(0xFFF3E5F5), Color(0xFFC8E6C9))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task List") },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_task") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading && uiState.taskList.isEmpty()) { // Chỉ hiện loading khi danh sách rỗng
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.taskList.isEmpty()) {
                Text(
                    text = "No tasks yet. Add one!",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.taskList, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            color = colors[uiState.taskList.indexOf(task) % colors.size],
                            onDelete = { taskViewModel.deleteTask(task.id) },
                            onEdit = {
                                val encodedTitle = URLEncoder.encode(task.title, StandardCharsets.UTF_8.toString())
                                val encodedDesc = URLEncoder.encode(task.description, StandardCharsets.UTF_8.toString())
                                navController.navigate("edit_task/${task.id}/$encodedTitle/$encodedDesc")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, color: Color, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEdit),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = task.description, fontSize = 16.sp)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Task", tint = Color.Gray)
            }
        }
    }
}