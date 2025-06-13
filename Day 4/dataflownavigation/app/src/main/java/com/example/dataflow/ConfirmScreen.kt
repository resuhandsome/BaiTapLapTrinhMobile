package com.example.dataflow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ConfirmScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    AuthScreenLayout(navController = navController) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Success",
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Xác nhận",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Chúng tôi ở đây để giúp bạn!",
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = {},
            readOnly = true,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "●●●●●●●●",
            onValueChange = {},
            readOnly = true,
            label = { Text("Mật khẩu") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.clearData()
                navController.popBackStack(ForgotPasswordRoutes.ENTER_EMAIL, inclusive = false)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Hoàn tất", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
