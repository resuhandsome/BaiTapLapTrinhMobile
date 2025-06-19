package com.example.firebase

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // SỬ DỤNG LOGO CỦA BẠN
            Image(
                painter = painterResource(id = R.drawable.logouth),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "SmartTasks",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ready to explore? Log in to get started.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        // Thay đổi nút đăng nhập để hiển thị loading
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4)),
            enabled = !state.isSignInSuccessful // Vô hiệu hóa nút khi đang đăng nhập
        ) {
            if (state.isSignInSuccessful) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google), // Sẽ tạo icon này ở bước 5
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified // Không tô màu cho icon gốc
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "SIGN IN WITH GOOGLE", color = Color.White)
            }
        }

        Text(
            text = "© UTH-SmartTasks",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}