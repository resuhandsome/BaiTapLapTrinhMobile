package com.example.dataflow

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var verificationCode by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    // Hàm để cập nhật email từ UI
    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun validateEmailFormat(): Boolean {
        if (email.isBlank()) {
            return false
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onVerificationCodeChange(newCode: String) {
        if (newCode.length <= 4 && newCode.all { it.isDigit() }) {
            verificationCode = newCode
        }
    }

    fun onNewPasswordChange(newPass: String) {
        newPassword = newPass
    }

    fun onConfirmPasswordChange(confirmPass: String) {
        confirmPassword = confirmPass
    }

    fun clearData() {
        email = ""
        verificationCode = ""
        newPassword = ""
        confirmPassword = ""
    }
}
