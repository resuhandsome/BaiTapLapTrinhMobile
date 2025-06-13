package com.example.dataflow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EnterEmailScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    var isEmailError by remember { mutableStateOf(false) }

    AuthScreenLayout(navController = navController, showBackButton = false) {
        Text(
            text = "Quên Mật khẩu?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Nhập Email của bạn để nhận mã xác thực!",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = {
                viewModel.onEmailChange(it)
                if (isEmailError) {
                    isEmailError = false
                }
            },
            label = { Text("Email của bạn") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),

            isError = isEmailError,

            supportingText = {
                if (isEmailError) {
                    Text(
                        text = "Vui lòng nhập email hợp lệ.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (viewModel.validateEmailFormat()) {
                    isEmailError = false
                    navController.navigate(ForgotPasswordRoutes.VERIFY_CODE)
                } else {
                    isEmailError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Tiếp theo", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
