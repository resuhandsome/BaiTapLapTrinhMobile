package com.example.quanlythuvien

import androidx.compose.foundation.clickable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    val studentsList = viewModel.students
    val currentStudent by viewModel.currentStudent

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh Sách Sinh Viên", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(ScreenRoutes.ADD_STUDENT_SCREEN)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Thêm sinh viên mới")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(studentsList, key = { it.id }) { student ->
                StudentListItem(
                    student = student,
                    isCurrent = student.id == currentStudent?.id,
                    onSelectStudent = {
                        viewModel.setCurrentStudentById(student.id)
                        navController.navigate(BottomNavItem.Manage.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    onDeleteStudent = {
                        viewModel.deleteStudent(student)
                    }
                )
            }
        }
    }
}

@Composable
fun StudentListItem(
    student: Student,
    isCurrent: Boolean,
    onSelectStudent: () -> Unit,
    onDeleteStudent: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelectStudent),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrent) 4.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
            )
            IconButton(onClick = onDeleteStudent) {
                Icon(Icons.Filled.Delete, contentDescription = "Xóa sinh viên", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
