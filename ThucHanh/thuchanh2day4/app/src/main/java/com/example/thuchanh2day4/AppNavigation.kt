package com.example.thuchanh2day4

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class ScreenRoute(val route: String) {
    object Splash : ScreenRoute("splash_screen")
    object Onboarding : ScreenRoute("onboarding_screen")
    object MainApp : ScreenRoute("main_app_screen")
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.Splash.route) {
        composable(ScreenRoute.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(ScreenRoute.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(ScreenRoute.MainApp.route) {
            MainAppScreen(navController = navController)
        }
    }
}
