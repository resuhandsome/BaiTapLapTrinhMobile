package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object TextDetail : Screen("text_detail_screen")
    object ImageDetail : Screen("image_detail_screen")
    object TextFieldDetail : Screen("textfield_detail_screen")
    object RowDetail : Screen("row_detail_screen")
    object ColumnDetail : Screen("colum_detail_screen")
    object PasswordFieldDetail : Screen("passwordfield_detail_screen")
    object BoxDetail : Screen("box_detail_screen")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.TextDetail.route) {
            TextDetailScreen(navController = navController)
        }
        composable(route = Screen.ImageDetail.route) {
            ImageDetailScreen(navController = navController)
        }
        composable(route = Screen.TextFieldDetail.route) {
            TextFieldDetailScreen(navController = navController)
        }
        composable(route = Screen.RowDetail.route) {
            RowLayoutScreen(navController = navController)
        }
        composable(route = Screen.ColumnDetail.route) {
            ColumnLayoutScreen(navController = navController)
        }
        composable(route = Screen.PasswordFieldDetail.route) {
            PasswordFieldDetailScreen(navController = navController)
        }
        composable(route = Screen.BoxDetail.route) {
            BoxDetailScreen(navController = navController)
        }
    }
}