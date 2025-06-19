package com.example.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {

                        // Màn hình Login
                        composable("login") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    val user = googleAuthUiClient.getSignedInUser()!!
                                    val photoUrl = URLEncoder.encode(user.profilePictureUrl ?: "", StandardCharsets.UTF_8.toString())
                                    navController.navigate("profile/${user.username ?: "No Name"}/${user.email ?: "No Email"}/$photoUrl") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Đăng nhập thành công!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    val user = googleAuthUiClient.getSignedInUser()!!
                                    val photoUrl = URLEncoder.encode(user.profilePictureUrl ?: "", StandardCharsets.UTF_8.toString())
                                    navController.navigate("profile/${user.username ?: "No Name"}/${user.email ?: "No Email"}/$photoUrl") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                    viewModel.resetState()
                                }
                            }

                            // theo dõi trạng thái lỗi
                            LaunchedEffect(key1 = state.signInError) {
                                state.signInError?.let { error ->
                                    Toast.makeText(applicationContext, "Lỗi: $error", Toast.LENGTH_LONG).show()
                                }
                            }

                            LoginScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }

                        // Màn hình Profile
                        composable(
                            route = "profile/{name}/{email}/{photoUrl}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType },
                                navArgument("email") { type = NavType.StringType },
                                navArgument("photoUrl") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name")
                            val email = backStackEntry.arguments?.getString("email")
                            val photoUrl = URLDecoder.decode(backStackEntry.arguments?.getString("photoUrl"), StandardCharsets.UTF_8.toString())

                            ProfileScreen(
                                name = name,
                                email = email,
                                photoUrl = photoUrl,
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(applicationContext, "Đã đăng xuất", Toast.LENGTH_SHORT).show()

                                        navController.navigate("login") {
                                            popUpTo(navController.graph.startDestinationId) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
