package com.example.dataflow

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object ForgotPasswordRoutes {
    const val ENTER_EMAIL = "enter_email"
    const val VERIFY_CODE = "verify_code"
    const val CREATE_NEW_PASSWORD = "create_new_password"
    const val CONFIRM = "confirm"
}

@Composable
fun ForgotPasswordNav() {
    val navController = rememberNavController()
    val viewModel: ForgotPasswordViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = ForgotPasswordRoutes.ENTER_EMAIL
    ) {
        composable(ForgotPasswordRoutes.ENTER_EMAIL) {
            EnterEmailScreen(navController = navController, viewModel = viewModel)
        }
        composable(ForgotPasswordRoutes.VERIFY_CODE) {
            VerifyCodeScreen(navController = navController, viewModel = viewModel)
        }
        composable(ForgotPasswordRoutes.CREATE_NEW_PASSWORD) {
            CreateNewPasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(ForgotPasswordRoutes.CONFIRM) {
            ConfirmScreen(navController = navController, viewModel = viewModel)
        }
    }
}
