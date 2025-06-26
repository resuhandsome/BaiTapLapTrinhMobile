package com.example.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination = if (googleAuthUiClient.getSignedInUser() != null) "task_list" else "login"

                    NavHost(navController = navController, startDestination = startDestination) {

                        composable("login") {
                            val signInViewModel = viewModel<SignInViewModel>()
                            val state by signInViewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(applicationContext, "Đăng nhập thành công!", Toast.LENGTH_LONG).show()
                                    navController.navigate("task_list") { popUpTo(navController.graph.startDestinationId) { inclusive = true } }
                                    signInViewModel.resetState()
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                                            signInViewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LoginScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build())
                                    }
                                }
                            )
                        }

                        composable("task_list") {
                            TaskListScreen(navController = navController, taskViewModel = taskViewModel)
                        }

                        composable("add_task") {
                            AddTaskScreen(navController = navController, taskViewModel = taskViewModel)
                        }

                        composable(
                            route = "edit_task/{taskId}/{title}/{description}",
                            arguments = listOf(
                                navArgument("taskId") { type = NavType.StringType },
                                navArgument("title") { type = NavType.StringType },
                                navArgument("description") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            val title = URLDecoder.decode(backStackEntry.arguments?.getString("title"), StandardCharsets.UTF_8.toString())
                            val description = URLDecoder.decode(backStackEntry.arguments?.getString("description"), StandardCharsets.UTF_8.toString())

                            EditTaskScreen(
                                navController = navController,
                                taskViewModel = taskViewModel,
                                taskId = taskId,
                                initialTitle = title,
                                initialDescription = description
                            )
                        }


                        composable("profile") {
                            val user = googleAuthUiClient.getSignedInUser()
                            ProfileScreen(
                                name = user?.username,
                                email = user?.email,
                                photoUrl = user?.profilePictureUrl,
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(applicationContext, "Đã đăng xuất", Toast.LENGTH_SHORT).show()
                                        navController.navigate("login") { popUpTo(navController.graph.startDestinationId) { inclusive = true } }
                                    }
                                },
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}