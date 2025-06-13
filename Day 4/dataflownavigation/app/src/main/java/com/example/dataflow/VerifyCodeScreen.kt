package com.example.dataflow

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun VerifyCodeScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    AuthScreenLayout(navController = navController) {
        Text(
            text = "Xác thực mã",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Nhập mã chúng tôi vừa gửi cho bạn qua Email đã đăng ký",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))

        // OTP Input Field
        OtpTextField(
            otpText = viewModel.verificationCode,
            onOtpTextChange = { value, _ -> viewModel.onVerificationCodeChange(value) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(ForgotPasswordRoutes.CREATE_NEW_PASSWORD) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = viewModel.verificationCode.length == 4
        ) {
            Text("Tiếp theo", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it, it.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    val char = when {
                        index >= otpText.length -> ""
                        else -> otpText[index].toString()
                    }
                    val isFocused = otpText.length == index
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .border(
                                width = 2.dp,
                                color = if (isFocused) MaterialTheme.colorScheme.primary else Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = char,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}
