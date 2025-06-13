package com.example.quanlythuvien

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Manage : BottomNavItem("manage_screen", Icons.Filled.Home, "Quản lý")
    object AllBooks : BottomNavItem("all_books_screen", Icons.Filled.MenuBook, "DS Sách")
    object Students : BottomNavItem("students_screen", Icons.Filled.AccountCircle, "Sinh viên")
}


object ScreenRoutes {
    const val ADD_BOOK_SCREEN = "add_book_screen"
    const val ADD_STUDENT_SCREEN = "add_student_screen"
}



@Composable
fun LibraryApp(viewModel: LibraryViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val items = listOf(
                    BottomNavItem.Manage,
                    BottomNavItem.AllBooks,
                    BottomNavItem.Students
                )
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Manage.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Manage.route) {
                MainScreen(navController = navController, viewModel = viewModel)
            }
            composable(BottomNavItem.AllBooks.route) {
                AllBooksScreen(navController = navController, viewModel = viewModel)
            }
            composable(BottomNavItem.Students.route) {
                StudentsScreen(navController = navController, viewModel = viewModel)
            }
            composable(ScreenRoutes.ADD_BOOK_SCREEN) {
                AddBookScreen(navController = navController, viewModel = viewModel)
            }
            composable(ScreenRoutes.ADD_STUDENT_SCREEN) {
                AddStudentScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}
